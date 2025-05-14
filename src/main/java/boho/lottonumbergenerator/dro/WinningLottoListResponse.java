package boho.lottonumbergenerator.dro;

import java.time.LocalDateTime;

import boho.lottonumbergenerator.entity.GeneratedLotto;
import lombok.Builder;

@Builder
public record WinningLottoListResponse(
	Long id,
	LocalDateTime createdAt,
	Integer firstNumber,
	Integer secondNumber,
	Integer thirdNumber,
	Integer fourthNumber,
	Integer fifthNumber,
	Integer sixthNumber) {

	public static WinningLottoListResponse of(GeneratedLotto generatedLotto) {
		return WinningLottoListResponse.builder()
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
