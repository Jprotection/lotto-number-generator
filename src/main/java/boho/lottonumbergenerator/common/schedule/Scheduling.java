package boho.lottonumbergenerator.common.schedule;

import java.util.Optional;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import boho.lottonumbergenerator.common.cache.CacheType;
import boho.lottonumbergenerator.service.GeneratedLottoService;
import boho.lottonumbergenerator.service.OfficialLottoService;
import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduling {

	private final CacheManager cacheManager;
	private final OfficialLottoService officialLottoService;
	private final GeneratedLottoService generatedLottoService;

	@Scheduled(cron = "${lotto.schedule.update-time}")
	public void fetchLottoAndClearCache() {
		// 최신 회차의 로또 결과 로딩
		officialLottoService.fetchLatestOfficialLotto();

		// 일주일간 생성된 모든 로또의 등수 확인 및 없데이트
		generatedLottoService.fetchAllWinningLotto();

		// 캐시 삭제
		Optional.ofNullable(cacheManager.getCache(CacheType.WINNING_LOTTO.getCacheName()))
			.ifPresent(Cache::clear);
	}
}
