package br.com.plgbr.urlshortener.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import br.com.plgbr.urlshortener.services.UrlService;

@Controller
public class UrlController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);

	protected static final String JSON = MediaType.APPLICATION_JSON_UTF8_VALUE;
	protected static final String TEXT = MediaType.TEXT_PLAIN_VALUE;

	@Autowired
	private UrlService urlService;

	@RequestMapping(value = "/api/v1/url/${longurl}", produces = TEXT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createShortUrl(@PathVariable("longURL") String longUrl) {
		LOGGER.info("creating shorturl for {} ", longUrl);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@RequestMapping(value = "${shorturl}", method = RequestMethod.GET)
	@ResponseBody
	public RedirectView redirect(@PathVariable("shorturl") String shortUrl) {
		LOGGER.info("redirecting user to desired url for {} ", shortUrl);
		RedirectView redirectView = new RedirectView();
		String longUrl = urlService.lookupLongURL(shortUrl);
		redirectView.setUrl("http://www.yahoo.com");
		return redirectView;
	}

}
