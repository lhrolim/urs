package br.com.plgbr.urlshortener.services;

import org.springframework.stereotype.Component;

import br.com.plgbr.urlshortener.exception.LongUrlNotFoundException;

@Component
public class UrlService {

	public String lookupLongURL(String shortUrl) throws LongUrlNotFoundException {
		return null;
	}

}
