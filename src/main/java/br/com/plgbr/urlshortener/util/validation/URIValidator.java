package br.com.plgbr.urlshortener.util.validation;

import java.net.URI;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class URIValidator implements ConstraintValidator<URIValidatorConstraint, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			if (value == null) {
				return true;
			}
			URI uri = new URI(value);
			return uri.isAbsolute();
		} catch (Exception e) {
			return false;
		}

	}

}
