package br.com.plgbr.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.plgbr.urlshortener.controller.dto.response.UrlStatisticsResponseDTO;
import br.com.plgbr.urlshortener.exception.LongUrlNotFoundException;
import br.com.plgbr.urlshortener.services.api.IURLFacade;
import br.com.plgbr.urlshortener.services.internal.statistics.StatisticsCacheDTO;

@RestController
public class StatisticsController {

	@Autowired
	private IURLFacade urlService;

	@RequestMapping(value = "/api/v1/statistics/{shorturl}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> redirect(@PathVariable("shorturl") String shortUrl) {
		if (shortUrl == null) {
			return ResponseEntity.badRequest().body("short url is required");
		}
		try {
			StatisticsCacheDTO statistics = urlService.lookupStatistics(shortUrl);
			return ResponseEntity.status(HttpStatus.OK).body(UrlStatisticsResponseDTO.fromEntity(statistics));
		} catch (LongUrlNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

}
