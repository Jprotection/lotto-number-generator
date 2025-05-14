package boho.lottonumbergenerator.config.cache;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableCaching
@EnableScheduling
@RequiredArgsConstructor
public class CacheConfig {

	private final CacheManager cacheManager;

	@Bean
	public CacheManager cacheManager() {
		List<CaffeineCache> caches = Arrays.stream(CacheType.values())
			.map(cacheType -> new CaffeineCache(cacheType.getCacheName(),
					Caffeine.newBuilder().recordStats()
						.expireAfterWrite(cacheType.getExpireAfterWrite(), TimeUnit.DAYS)
						.maximumSize(cacheType.getMaximumSize())
						.build()
				)
			)
			.toList();

		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(caches);

		return cacheManager;
	}

	@Scheduled(cron = "${lotto.cron}")
	public void clearWinningLottoCache() {
		Optional.ofNullable(cacheManager.getCache(CacheType.WINNING_LOTTO.getCacheName()))
			.ifPresent(Cache::clear);
	}
}
