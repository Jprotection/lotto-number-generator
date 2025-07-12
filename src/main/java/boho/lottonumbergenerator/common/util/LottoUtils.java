package boho.lottonumbergenerator.common.util;

import boho.lottonumbergenerator.entity.lotto.GeneratedLotto;

public abstract class LottoUtils {

	public static String getSummary(GeneratedLotto generatedLotto) {
		String defaultSummary = generatedLotto.getDrawDate() + " " + generatedLotto.getDrawNumber() + "회차 ";

		if (!generatedLotto.isChecked()) {
			return defaultSummary + "결과 대기 중";
		}

		if (generatedLotto.getPrizeRank() == 0) {
			return defaultSummary + "미당첨";
		}

		return defaultSummary + generatedLotto.getPrizeRank() + "등 당첨";
	}
}
