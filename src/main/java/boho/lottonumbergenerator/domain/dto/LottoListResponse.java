package boho.lottonumbergenerator.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import boho.lottonumbergenerator.domain.entity.lotto.GeneratedLotto;
import boho.lottonumbergenerator.domain.entity.member.Member;
import lombok.Builder;

@Builder
public record LottoListResponse(
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

	public static LottoListResponse of(GeneratedLotto generatedLotto) {
		return LottoListResponse.builder()
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
