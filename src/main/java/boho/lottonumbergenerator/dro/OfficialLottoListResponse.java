package boho.lottonumbergenerator.dro;

import java.time.LocalDate;

import boho.lottonumbergenerator.entity.OfficialLotto;
import lombok.Builder;

@Builder
public record OfficialLottoListResponse(
	Long drawNumber,
	LocalDate drawDate,
	Long totalSalesAmount,
	Integer firstPrizeWinnerCount,
	Long firstPrizeAmount,
	Long firstPrizeTotalAmount,
	Integer firstNumber,
	Integer secondNumber,
	Integer thirdNumber,
	Integer fourthNumber,
	Integer fifthNumber,
	Integer sixthNumber,
	Integer bonusNumber
) {

	public static OfficialLottoListResponse of(OfficialLotto lotto) {
		return OfficialLottoListResponse.builder()
			.drawNumber(lotto.getDrawNumber())
			.drawDate(lotto.getDrawDate())
			.totalSalesAmount(lotto.getTotalSalesAmount())
			.firstPrizeWinnerCount(lotto.getFirstPrizeWinnerCount())
			.firstPrizeAmount(lotto.getFirstPrizeAmount())
			.firstPrizeTotalAmount(lotto.getFirstPrizeTotalAmount())
			.firstNumber(lotto.getFirstNumber())
			.secondNumber(lotto.getSecondNumber())
			.thirdNumber(lotto.getThirdNumber())
			.fourthNumber(lotto.getFourthNumber())
			.fifthNumber(lotto.getFifthNumber())
			.sixthNumber(lotto.getSixthNumber())
			.bonusNumber(lotto.getBonusNumber())
			.build();

	}
}
