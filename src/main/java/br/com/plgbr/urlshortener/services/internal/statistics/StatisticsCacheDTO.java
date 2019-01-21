package br.com.plgbr.urlshortener.services.internal.statistics;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import br.com.plgbr.urlshortener.model.entities.UrlStatistics;

@RedisHash("StatisticsCache")
public class StatisticsCacheDTO {

	@Id
	public String shortURL;
	public Long hits = 0L;
	public int incrementCount;
	public Date lastAccessed;

	public StatisticsCacheDTO(String shortUrl, UrlStatistics storedStatistics) {
		this.shortURL = shortUrl;
		this.hits = storedStatistics.getHits();
		this.incrementCount = 0;
		this.lastAccessed = Calendar.getInstance().getTime();
	}

	public StatisticsCacheDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getHits() {
		return hits;
	}

	public void setHits(Long hits) {
		this.hits = hits;
	}

	public int getIncrementCount() {
		return incrementCount;
	}

	public void setIncrementCount(int incrementCount) {
		this.incrementCount = incrementCount;
	}

	public String getShortURL() {
		return shortURL;
	}

	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}

	public Date getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shortURL == null) ? 0 : shortURL.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatisticsCacheDTO other = (StatisticsCacheDTO) obj;
		if (shortURL == null) {
			if (other.shortURL != null)
				return false;
		} else if (!shortURL.equals(other.shortURL))
			return false;
		return true;
	}

	public void increment() {
		this.incrementCount++;
		this.hits++;
		this.lastAccessed = Calendar.getInstance().getTime();
	}

}
