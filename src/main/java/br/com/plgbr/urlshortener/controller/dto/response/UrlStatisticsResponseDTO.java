package br.com.plgbr.urlshortener.controller.dto.response;

import java.io.Serializable;
import java.util.Date;

import br.com.plgbr.urlshortener.services.internal.statistics.StatisticsCacheDTO;

//TODO: perhaps we could come up with better solution to pick which fields to serialize from the model
public class UrlStatisticsResponseDTO implements Serializable {

	private static final long serialVersionUID = 7859326318698724720L;

	public Long hits = 0L;
	public Date lastAccessed;

	public Long getHits() {
		return hits;
	}

	public void setHits(Long hits) {
		this.hits = hits;
	}

	public Date getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public static UrlStatisticsResponseDTO fromEntity(StatisticsCacheDTO statistics) {
		UrlStatisticsResponseDTO urlStatisticsResponseDTO = new UrlStatisticsResponseDTO();
		if (statistics == null) {
			return urlStatisticsResponseDTO;
		}
		urlStatisticsResponseDTO.setHits(statistics.getHits());
		urlStatisticsResponseDTO.setLastAccessed(statistics.getLastAccessed());
		return urlStatisticsResponseDTO;

	}

}
