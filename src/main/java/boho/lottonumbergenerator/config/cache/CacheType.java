package boho.lottonumbergenerator.config.cache;

import lombok.Getter;

@Getter
public enum CacheType {

	WINNING_LOTTO("winning_lotto", 7, 50);

	private final String cacheName;
	private final int expireAfterWrite;
	private final int maximumSize;

	CacheType(String cacheName, int expireAfterWrite, int maximumSize) {
		this.cacheName = cacheName;
		this.expireAfterWrite = expireAfterWrite;
		this.maximumSize = maximumSize;
	}
}
