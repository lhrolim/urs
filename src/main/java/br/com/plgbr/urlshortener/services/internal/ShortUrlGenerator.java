package br.com.plgbr.urlshortener.services.internal;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlGenerator {

	@Value("${urs.shorturlLength}")
	private int shorUrlLength;

	static final String AB = "0123456789abcdefghijklmnopqrstuvwxyz"; // 36^n possibilities where n is the length
	static SecureRandom rnd = new SecureRandom();

	public String buildShortUrl() {
		StringBuilder sb = new StringBuilder(shorUrlLength);
		for (int i = 0; i < shorUrlLength; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

}
