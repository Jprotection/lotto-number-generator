package boho.lottonumbergenerator.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import boho.lottonumbergenerator.common.util.LottoUtils;
import boho.lottonumbergenerator.domain.entity.lotto.GeneratedLotto;
import boho.lottonumbergenerator.domain.entity.member.Member;
import lombok.Builder;

@Builder
public record WinningLottoListResponse(
	String summary,
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
	Integer sixthNumber,
	List<Integer> includeNumbers,
	List<Integer> excludeNumbers) {

	public static WinningLottoListResponse of(GeneratedLotto generatedLotto) {
		return WinningLottoListResponse.builder()
			.summary(LottoUtils.getSummary(generatedLotto))
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
			.includeNumbers(generatedLotto.getIncludeNumbers())
			.excludeNumbers(generatedLotto.getExcludeNumbers())
			.build();
	}
}
