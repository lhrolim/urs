package br.com.plgbr.urlshortener.services.api;

public class UrlRepresentation {
	String url;
	Boolean created;
	Boolean fromCache;

	public UrlRepresentation(String url, Boolean created, Boolean fromCache) {
		super();
		this.url = url;
		this.created = created;
		this.fromCache = fromCache;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getCreated() {
		return created;
	}

	public void setCreated(Boolean created) {
		this.created = created;
	}

	public Boolean getFromCache() {
		return fromCache;
	}

	public void setFromCache(Boolean fromCache) {
		this.fromCache = fromCache;
	}
}
