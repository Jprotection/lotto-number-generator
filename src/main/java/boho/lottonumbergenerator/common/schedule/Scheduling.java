package boho.lottonumbergenerator.common.schedule;

import java.util.Optional;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import boho.lottonumbergenerator.common.cache.CacheType;
import boho.lottonumbergenerator.service.OfficialLottoService;
import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduling {

	private final CacheManager cacheManager;
	private final OfficialLottoService officialLottoService;

	@Scheduled(cron = "${lotto.schedule.update-time}")
	public void fetchOfficialLottoAndClearCache() {
		// 최신 회차의 로또 결과 로딩
		officialLottoService.fetchLatestOfficialLotto();

		// 캐시 삭제
		Optional.ofNullable(cacheManager.getCache(CacheType.WINNING_LOTTO.getCacheName()))
			.ifPresent(Cache::clear);
	}
}
