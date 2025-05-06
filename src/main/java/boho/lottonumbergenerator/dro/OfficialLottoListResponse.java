package boho.lottonumbergenerator.dro;

import java.time.LocalDate;

import boho.lottonumbergenerator.entity.OfficialLotto;
import lombok.Builder;

@Builder
public record OfficialLottoListResponse(
	boolean resultStatus,
	Integer drawNumber,
	LocalDate drawDate,
	Long totalSalesAmount,
	Integer firstPrizeWinnerCount,
	Long firstPrizeAmount,
	Long firstPrizeTotalAmount,
	Integer number1,
	Integer number2,
	Integer number3,
	Integer number4,
	Integer number5,
	Integer number6,
	Integer bonusNumber
) {

	public static OfficialLottoListResponse of(OfficialLotto lotto) {
		return OfficialLottoListResponse.builder()
			.drawNumber(lotto.getDrawNumber())
			.resultStatus(lotto.isResultStatus())
			.drawDate(lotto.getDrawDate())
			.totalSalesAmount(lotto.getTotalSalesAmount())
			.firstPrizeWinnerCount(lotto.getFirstPrizeWinnerCount())
			.firstPrizeAmount(lotto.getFirstPrizeAmount())
			.firstPrizeTotalAmount(lotto.getFirstPrizeTotalAmount())
			.number1(lotto.getNumber1())
			.number2(lotto.getNumber2())
			.number3(lotto.getNumber3())
			.number4(lotto.getNumber4())
			.number5(lotto.getNumber5())
			.number6(lotto.getNumber6())
			.bonusNumber(lotto.getBonusNumber())
			.build();

	}
}
