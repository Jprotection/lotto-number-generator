package boho.lottonumbergenerator.dro;

import java.time.LocalDate;

import boho.lottonumbergenerator.entity.lotto.OfficialLotto;
import lombok.Builder;

@Builder
public record OfficialLottoResponse(
	Long drawNumber,
	LocalDate drawDate,
	Integer firstNumber,
	Integer secondNumber,
	Integer thirdNumber,
	Integer fourthNumber,
	Integer fifthNumber,
	Integer sixthNumber,
	Integer bonusNumber) {

	public static OfficialLottoResponse of(OfficialLotto lotto) {
		return OfficialLottoResponse.builder()
			.drawNumber(lotto.getDrawNumber())
			.drawDate(lotto.getDrawDate())
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
