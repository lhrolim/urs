package br.com.plgbr.urlshortener.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.plgbr.urlshortener.model.entities.UrlModel;

public interface UrlRepository extends CrudRepository<UrlModel, Integer> {

	Optional<UrlModel> findByShortUrl(@Param("shortUrl") String shortUrl);

	Optional<UrlModel> findByLongUrl(@Param("longUrl") String longUrl);

}
