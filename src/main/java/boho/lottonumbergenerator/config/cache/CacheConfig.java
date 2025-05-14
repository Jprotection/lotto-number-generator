package boho.lottonumbergenerator.config.cache;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

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
}
