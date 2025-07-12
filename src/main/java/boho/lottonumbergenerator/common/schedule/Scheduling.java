package boho.lottonumbergenerator.common.schedule;

import java.util.Optional;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import boho.lottonumbergenerator.common.cache.CacheType;
import boho.lottonumbergenerator.repository.GeneratedLottoRepository;
import boho.lottonumbergenerator.service.LottoApiService;
import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduling {

	private final CacheManager cacheManager;
	private final LottoApiService lottoApiService;
	private final GeneratedLottoRepository generatedLottoRepository;

	@Scheduled(cron = "${lotto.schedule.update-time}")
	public void fetchLatestOfficialLotto() {
		lottoApiService.fetchLatestOfficialLotto();
	}

	@Scheduled(cron = "${lotto.schedule.update-time}")
	public void clearWinningLottoCache() {
		Optional.ofNullable(cacheManager.getCache(CacheType.WINNING_LOTTO.getCacheName()))
			.ifPresent(Cache::clear);
	}

	@Scheduled(cron = "${lotto.schedule.delete-time}")
	public void deleteNonWinningLotto() {
		generatedLottoRepository.deleteAll(generatedLottoRepository.findByPrizeRank(0));
	}
}
