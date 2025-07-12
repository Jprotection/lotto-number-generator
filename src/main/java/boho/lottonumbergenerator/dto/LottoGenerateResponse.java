package boho.lottonumbergenerator.dto;

import java.time.LocalDateTime;
import java.util.List;

import boho.lottonumbergenerator.common.util.LottoUtils;
import boho.lottonumbergenerator.entity.lotto.GeneratedLotto;
import boho.lottonumbergenerator.entity.member.Member;
import lombok.Builder;

@Builder
public record LottoGenerateResponse(

	String summary,
	Long id,
	Member creator,
	LocalDateTime createDate,
	Integer firstNumber,
	Integer secondNumber,
	Integer thirdNumber,
	Integer fourthNumber,
	Integer fifthNumber,
	Integer sixthNumber,
	List<Integer> includeNumbers,
	List<Integer> excludeNumbers) {

	public static LottoGenerateResponse of(GeneratedLotto generatedLotto) {
		return LottoGenerateResponse.builder()
			.summary(LottoUtils.getSummary(generatedLotto))
			.id(generatedLotto.getId())
			.creator(generatedLotto.getMember())
			.createDate(generatedLotto.getCreateDate())
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
