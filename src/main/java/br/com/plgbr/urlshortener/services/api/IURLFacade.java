package br.com.plgbr.urlshortener.services.api;

import java.net.URI;

import br.com.plgbr.urlshortener.exception.LongUrlNotFoundException;
import br.com.plgbr.urlshortener.services.internal.statistics.StatisticsCacheDTO;

public interface IURLFacade {

	URI lookupLongURL(String shortUrl) throws LongUrlNotFoundException;

	UrlRepresentation buildShortUrl(String longUrl);

	StatisticsCacheDTO lookupStatistics(String shortUrl);

}
