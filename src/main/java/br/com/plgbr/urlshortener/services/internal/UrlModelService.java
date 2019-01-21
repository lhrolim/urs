package br.com.plgbr.urlshortener.services.internal;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.plgbr.urlshortener.exception.LongUrlNotFoundException;
import br.com.plgbr.urlshortener.model.entities.UrlModel;
import br.com.plgbr.urlshortener.model.repositories.UrlRepository;
import br.com.plgbr.urlshortener.services.api.UrlRepresentation;

@Service
public class UrlModelService {

	@Autowired
	private ShortUrlGenerator urlGenerator;

	@Autowired
	private UrlRepository urlRepository;

	@Autowired
	private UrlCacheService urlCacheService;

	public URI lookupLongURL(String shortUrl) throws LongUrlNotFoundException {
		if (shortUrl == null) {
			throw new IllegalArgumentException("short url parameter is required");
		}
		URI result = urlCacheService.lookupCache(shortUrl);
		if (result != null) {
			return result;
		}
		result = lookupDatabase(shortUrl);
		if (result == null) {
			throw new LongUrlNotFoundException(shortUrl);
		}
		return result;
	}

	private URI lookupDatabase(String shortUrl) {
		Optional<UrlModel> url = this.urlRepository.findByShortUrl(shortUrl);
		if (!url.isPresent()) {
			return null;
		}
		try {
			return new URI(url.get().getLongUrl());
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public UrlRepresentation buildShortUrl(String longUrl) {
		if (longUrl == null) {
			throw new IllegalArgumentException("long url is required");
		}

		UrlRepresentation result = null;
		int retries = 0;
		while (result == null && retries < 5) {
			// if a collision occurs the insert would fail at the database due to a unique
			// key constraint
			result = doBuildShortUrl(longUrl);
			retries++; // playing safe to avoid an infinite loop on an extreme situation
		}
		return result;
	}

	private UrlRepresentation doBuildShortUrl(String longUrl) {
		String shortUrl = this.urlGenerator.buildShortUrl();
		try {
			urlRepository.save(UrlModel.getInstance(shortUrl, longUrl));
			return new UrlRepresentation(shortUrl, true, false);
		} catch (DataIntegrityViolationException constraintException) {
			if (!(constraintException.getCause() instanceof ConstraintViolationException)) {
				throw constraintException; // playing safe, shouldn't happen
			}
			ConstraintViolationException cause = (ConstraintViolationException) constraintException.getCause();
			String constraintName = cause.getConstraintName();
			if ("uq_longurl".equals(constraintName)) {
				// fetching just when it fails
				Optional<UrlModel> urlModel = this.urlRepository.findByLongUrl(longUrl);
				return new UrlRepresentation(urlModel.get().getShortUrl(), false, false);
			} else if ("uq_shorturl".equals(constraintName)) {
				// collisions should rarely happen, but just in case let's handle them
				return null;
			}
			throw constraintException;
		}
	}

}
