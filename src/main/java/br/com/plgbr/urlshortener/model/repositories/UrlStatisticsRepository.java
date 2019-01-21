package br.com.plgbr.urlshortener.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.plgbr.urlshortener.model.entities.UrlStatistics;

public interface UrlStatisticsRepository extends CrudRepository<UrlStatistics, Long> {

	Optional<UrlStatistics> findByShortUrl(@Param("url") String shortUrl);

}
