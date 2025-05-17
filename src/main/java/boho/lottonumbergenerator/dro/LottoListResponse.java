package boho.lottonumbergenerator.dro;

import java.time.LocalDateTime;

import boho.lottonumbergenerator.entity.lotto.GeneratedLotto;
import lombok.Builder;

@Builder
public record LottoListResponse(
	Long id,
	LocalDateTime createdAt,
	Integer firstNumber,
	Integer secondNumber,
	Integer thirdNumber,
	Integer fourthNumber,
	Integer fifthNumber,
	Integer sixthNumber) {

	public static LottoListResponse of(GeneratedLotto generatedLotto) {
		return LottoListResponse.builder()
			.id(generatedLotto.getId())
			.createdAt(generatedLotto.getCreatedAt())
			.firstNumber(generatedLotto.getFirstNumber())
			.secondNumber(generatedLotto.getSecondNumber())
			.thirdNumber(generatedLotto.getThirdNumber())
			.fourthNumber(generatedLotto.getFourthNumber())
			.fifthNumber(generatedLotto.getFifthNumber())
			.sixthNumber(generatedLotto.getSixthNumber())
			.build();
	}

}
