package br.com.plgbr.urlshortener.services.internal;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.plgbr.urlshortener.exception.LongUrlNotFoundException;
import br.com.plgbr.urlshortener.services.api.IURLFacade;
import br.com.plgbr.urlshortener.services.api.UrlRepresentation;
import br.com.plgbr.urlshortener.services.internal.statistics.StatisticsCacheDTO;
import br.com.plgbr.urlshortener.services.internal.statistics.UrlStatisticsService;

@Service
public class UrlFacade implements IURLFacade {

	@Autowired
	private UrlStatisticsService urlStatisticsService;

	@Autowired
	private UrlModelService urlModelService;

	@Override
	public URI lookupLongURL(String shortUrl) throws LongUrlNotFoundException {
		URI longUrlRepresentation = urlModelService.lookupLongURL(shortUrl);
		if (longUrlRepresentation != null) {
			urlStatisticsService.updateStatistics(shortUrl);
		}
		return longUrlRepresentation;
	}

	@Override
	public UrlRepresentation buildShortUrl(String longUrl) {
		return urlModelService.buildShortUrl(longUrl);
	}

	@Override
	public StatisticsCacheDTO lookupStatistics(String shortUrl) {
		return urlStatisticsService.lookupStatistics(shortUrl);
	}

}
