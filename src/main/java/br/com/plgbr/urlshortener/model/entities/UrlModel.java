package br.com.plgbr.urlshortener.model.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "urls")
public class UrlModel implements Serializable {

	private static final long serialVersionUID = -7847438739250612432L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, name = "shorturl")
	private String shortUrl;

	@Column(nullable = false, name = "longurl")
	private String longUrl;

	@Column(nullable = false, name = "creationdate")
	private Date creationDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public static UrlModel getInstance(String shortUrl, String longUrl) {
		UrlModel urlModel = new UrlModel();
		urlModel.setCreationDate(Calendar.getInstance().getTime());
		urlModel.setLongUrl(longUrl);
		urlModel.setShortUrl(shortUrl);
		return urlModel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shortUrl == null) ? 0 : shortUrl.hashCode());
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
		UrlModel other = (UrlModel) obj;
		if (shortUrl == null) {
			if (other.shortUrl != null)
				return false;
		} else if (!shortUrl.equals(other.shortUrl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UrlModel [shortUrl=" + shortUrl + ", longUrl=" + longUrl + ", creationDate=" + creationDate + "]";
	}

}