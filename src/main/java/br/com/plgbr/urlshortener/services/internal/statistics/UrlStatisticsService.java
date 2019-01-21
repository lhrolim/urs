package br.com.plgbr.urlshortener.services.internal.statistics;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.plgbr.urlshortener.exception.LongUrlNotFoundException;
import br.com.plgbr.urlshortener.model.entities.UrlModel;
import br.com.plgbr.urlshortener.model.entities.UrlStatistics;
import br.com.plgbr.urlshortener.model.repositories.UrlCachedStatisticsRepository;
import br.com.plgbr.urlshortener.model.repositories.UrlRepository;
import br.com.plgbr.urlshortener.model.repositories.UrlStatisticsRepository;

@Service
public class UrlStatisticsService {

	@Autowired
	private UrlStatisticsRepository repository;

	@Autowired
	private UrlRepository urlMainRepository;

	@Autowired
	private UrlCachedStatisticsRepository cachedRepository;

	public StatisticsCacheDTO lookupStatistics(String shortUrl) {
		StatisticsCacheDTO cachedStatistics = cachedRepository.findById(shortUrl).orElse(null);
		if (cachedStatistics == null) {
			Optional<UrlStatistics> url = repository.findByShortUrl(shortUrl);
			if (!url.isPresent()) {
				UrlModel model = urlMainRepository.findByShortUrl(shortUrl).orElse(null);
				if (model == null) {
					throw new LongUrlNotFoundException(shortUrl);
				}
			}
			UrlStatistics storedStatistics = url.orElse(new UrlStatistics());
			cachedStatistics = new StatisticsCacheDTO(shortUrl, storedStatistics);
			cachedRepository.save(cachedStatistics);
		}
		return cachedStatistics;

	}

	public void updateStatistics(String shortUrl) {
		Optional<StatisticsCacheDTO> statistics = cachedRepository.findById(shortUrl);
		StatisticsCacheDTO cachedStatistics = statistics.orElse(new StatisticsCacheDTO());
		cachedStatistics.increment();
		cachedRepository.save(cachedStatistics); // do not hit the database, rather just a cache
	}

}
