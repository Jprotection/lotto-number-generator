package boho.lottonumbergenerator.config;

import java.util.Optional;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import boho.lottonumbergenerator.config.cache.CacheType;
import boho.lottonumbergenerator.service.LottoApiService;
import lombok.RequiredArgsConstructor;

@EnableScheduling
@RequiredArgsConstructor
public class Scheduling {

	private final CacheManager cacheManager;
	private final LottoApiService lottoApiService;

	@Scheduled(cron = "${lotto.cron}")
	public void fetchLatestOfficialLotto() {
		lottoApiService.fetchLatestOfficialLotto();
	}

	@Scheduled(cron = "${lotto.cron}")
	public void clearWinningLottoCache() {
		Optional.ofNullable(cacheManager.getCache(CacheType.WINNING_LOTTO.getCacheName()))
			.ifPresent(Cache::clear);
	}
}
