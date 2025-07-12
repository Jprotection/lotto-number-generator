package boho.lottonumbergenerator.common.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

import boho.lottonumbergenerator.entity.lotto.GeneratedLotto;

public abstract class LottoUtils {

	private static final LocalTime cutoffTime = LocalTime.of(20, 0);

	public static String getSummary(GeneratedLotto generatedLotto) {
		LocalDateTime drawDateTime = generatedLotto.getDrawDate().atTime(cutoffTime);

		if (drawDateTime.isAfter(LocalDateTime.now())) {
			return "아직 결과가 나오지 않은 조합";
		}

		if (generatedLotto.getPrizeRank() == 0) {
			return generatedLotto.getDrawDate() + " " + generatedLotto.getDrawNumber() + "회차 미당첨";
		}

		return generatedLotto.getDrawDate() + " " + generatedLotto.getDrawNumber() + "회차 "
			+ generatedLotto.getPrizeRank() + "등 당첨";
	}
}
