package br.com.plgbr.urlshortener.controller.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import br.com.plgbr.urlshortener.util.validation.URIValidatorConstraint;

public class ShortUrlCreationDto implements Serializable {

	private static final long serialVersionUID = -3014804194322889016L;

	@NotEmpty(message = "parameter longUrl is required")
	@URIValidatorConstraint
	private String longUrl;

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

}
