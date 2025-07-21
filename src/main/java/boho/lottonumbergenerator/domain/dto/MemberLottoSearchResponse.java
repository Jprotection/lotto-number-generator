package boho.lottonumbergenerator.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import boho.lottonumbergenerator.common.util.LottoUtils;
import boho.lottonumbergenerator.domain.entity.lotto.GeneratedLotto;
import lombok.Builder;

@Builder
public record MemberLottoSearchResponse(
	String summary,
	Integer drawNumber,
	LocalDate drawDate,
	LocalDateTime createDate,
	Integer prizeRank,
	Integer firstNumber,
	Integer secondNumber,
	Integer thirdNumber,
	Integer fourthNumber,
	Integer fifthNumber,
	Integer sixthNumber,
	List<Integer> includeNumbers,
	List<Integer> excludeNumbers) {

	public static MemberLottoSearchResponse of(GeneratedLotto generatedLotto) {
		return MemberLottoSearchResponse.builder()
			.summary(LottoUtils.getSummary(generatedLotto))
			.drawNumber(generatedLotto.getDrawNumber())
			.drawDate(generatedLotto.getDrawDate())
			.createDate(generatedLotto.getCreateDate())
			.prizeRank(generatedLotto.getPrizeRank())
			.firstNumber(generatedLotto.getFirstNumber())
			.secondNumber(generatedLotto.getSecondNumber())
			.thirdNumber(generatedLotto.getThirdNumber())
			.fourthNumber(generatedLotto.getFourthNumber())
			.fifthNumber(generatedLotto.getFifthNumber())
			.sixthNumber(generatedLotto.getSixthNumber())
			.includeNumbers(generatedLotto.getIncludeNumbers())
			.excludeNumbers(generatedLotto.getExcludeNumbers())
			.build();
	}
}
