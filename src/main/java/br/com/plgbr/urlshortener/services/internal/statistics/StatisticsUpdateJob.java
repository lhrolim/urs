package br.com.plgbr.urlshortener.services.internal.statistics;

import javax.transaction.Transactional;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import br.com.plgbr.urlshortener.controller.UrlController;
import br.com.plgbr.urlshortener.model.entities.UrlStatistics;
import br.com.plgbr.urlshortener.model.repositories.UrlCachedStatisticsRepository;
import br.com.plgbr.urlshortener.model.repositories.UrlStatisticsRepository;

@Service
public class StatisticsUpdateJob extends QuartzJobBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);

	@Autowired
	private UrlCachedStatisticsRepository cachedRepository;

	@Autowired
	private UrlStatisticsRepository urlRepository;

	@Override
	@Transactional
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Iterable<StatisticsCacheDTO> statisticsCacheToIncrement = cachedRepository.findAll();
		for (StatisticsCacheDTO cacheDTO : statisticsCacheToIncrement) {
			UrlStatistics statistics = urlRepository.findByShortUrl(cacheDTO.getShortURL()).orElse(new UrlStatistics());
			LOGGER.debug("consolidating statistcs for {}", cacheDTO.shortURL);
			statistics.setShortUrl(cacheDTO.shortURL);
			statistics.incrementHits(cacheDTO.incrementCount, cacheDTO.getLastAccessed());
			urlRepository.save(statistics);
			cacheDTO.setIncrementCount(0);
			cachedRepository.save(cacheDTO);
		}
	}

}
