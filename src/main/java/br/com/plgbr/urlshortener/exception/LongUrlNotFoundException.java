package br.com.plgbr.urlshortener.exception;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class LongUrlNotFoundException extends ResourceNotFoundException {

	private static final long serialVersionUID = 6844757303760159206L;

	public LongUrlNotFoundException(String shortUrl) {
		super(String.format("Long url not found for {}", shortUrl));
	}

}
