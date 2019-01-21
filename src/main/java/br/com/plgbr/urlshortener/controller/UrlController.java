package br.com.plgbr.urlshortener.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.plgbr.urlshortener.controller.dto.request.ShortUrlCreationInputDto;
import br.com.plgbr.urlshortener.exception.LongUrlNotFoundException;
import br.com.plgbr.urlshortener.services.api.IURLFacade;
import br.com.plgbr.urlshortener.services.api.UrlRepresentation;
import br.com.plgbr.urlshortener.util.validation.ValidationErrorBuilder;

@Controller
public class UrlController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);

	protected static final String JSON = MediaType.APPLICATION_JSON_UTF8_VALUE;
	protected static final String TEXT = MediaType.TEXT_PLAIN_VALUE;

	@Autowired
	private IURLFacade urlService;

	@RequestMapping(value = "/api/v1/url", produces = JSON, method = RequestMethod.POST, consumes = JSON)
	@ResponseBody
	public ResponseEntity<Object> createShortUrl(@RequestBody @Valid ShortUrlCreationInputDto shortUrlCreationDTO,
			Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
		}

		String longUrl = shortUrlCreationDTO.getLongUrl();
		LOGGER.info("creating short url for {} ", longUrl);
		UrlRepresentation urlRepresentation = urlService.buildShortUrl(longUrl);
		if (urlRepresentation.getCreated()) {
			LOGGER.info("shorturl created {} ", urlRepresentation.getUrl());
		}

		return ResponseEntity.status(HttpStatus.OK).body(urlRepresentation);
	}

	@RequestMapping(value = "/{shorturl}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> redirect(@PathVariable("shorturl") String shortUrl) {
		if (shortUrl == null) {
			return ResponseEntity.badRequest().body("short url is required");
		}

		LOGGER.info("redirecting user to desired url for {} ", shortUrl);
		try {
			URI longUrl = urlService.lookupLongURL(shortUrl);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(longUrl);
			return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
		} catch (LongUrlNotFoundException e) {
			LOGGER.info("long url not found for {} ", shortUrl);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
