package boho.lottonumbergenerator.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import boho.lottonumbergenerator.domain.entity.lotto.GeneratedLotto;
import boho.lottonumbergenerator.domain.entity.member.Member;
import lombok.Builder;

@Builder
public record WinningLottoListResponse(
	Long id,
	Member member,
	Integer drawNumber,
	LocalDate drawDate,
	LocalDateTime createDate,
	Integer prizeRank,
	Integer firstNumber,
	Integer secondNumber,
	Integer thirdNumber,
	Integer fourthNumber,
	Integer fifthNumber,
	Integer sixthNumber) {

	public static WinningLottoListResponse of(GeneratedLotto generatedLotto) {
		return WinningLottoListResponse.builder()
			.id(generatedLotto.getId())
			.member(generatedLotto.getMember())
			.drawNumber(generatedLotto.getDrawNumber())
			.drawDate(generatedLotto.getDrawDate())
			.prizeRank(generatedLotto.getPrizeRank())
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
