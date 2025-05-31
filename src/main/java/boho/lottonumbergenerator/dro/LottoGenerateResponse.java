package boho.lottonumbergenerator.dro;

import java.time.LocalDateTime;

import boho.lottonumbergenerator.entity.lotto.GeneratedLotto;
import lombok.Builder;

@Builder
public record LottoGenerateResponse(

	Long id,
	LocalDateTime createDate,
	Integer firstNumber,
	Integer secondNumber,
	Integer thirdNumber,
	Integer fourthNumber,
	Integer fifthNumber,
	Integer sixthNumber) {

	public static LottoGenerateResponse of(GeneratedLotto generatedLotto) {
		return LottoGenerateResponse.builder()
			.id(generatedLotto.getId())
			.createDate(generatedLotto.getCreateDate())
			.firstNumber(generatedLotto.getFirstNumber())
			.secondNumber(generatedLotto.getSecondNumber())
			.thirdNumber(generatedLotto.getThirdNumber())
			.fourthNumber(generatedLotto.getFourthNumber())
			.fifthNumber(generatedLotto.getFifthNumber())
			.sixthNumber(generatedLotto.getSixthNumber())
			.build();
	}
}
