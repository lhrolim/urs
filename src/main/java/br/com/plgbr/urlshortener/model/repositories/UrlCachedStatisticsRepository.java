package br.com.plgbr.urlshortener.model.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.plgbr.urlshortener.services.internal.statistics.StatisticsCacheDTO;

public interface UrlCachedStatisticsRepository extends CrudRepository<StatisticsCacheDTO, String> {

}
