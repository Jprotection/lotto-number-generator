package boho.lottonumbergenerator.domain.entity.lotto;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiPredicate;

import lombok.Getter;

@Getter
public enum WinningRank {
	FIRST(1, "1등", (g, o) -> getMatchCount(g, o) == 6),
	SECOND(2, "2등", (g, o) -> getMatchCount(g, o) == 5 && g.toNumberList().contains(o.getBonusNumber())),
	THIRD(3, "3등", (g, o) -> getMatchCount(g, o) == 5 && !g.toNumberList().contains(o.getBonusNumber())),
	FOURTH(4, "4등", (g, o) -> getMatchCount(g, o) == 4),
	FIFTH(5, "5등", (g, o) -> getMatchCount(g, o) == 3),
	NOTHING(0, "미당첨", (g, o) -> getMatchCount(g, o) <= 2);

	private final Integer rank;
	private final String description;
	private final BiPredicate<GeneratedLotto, OfficialLotto> matchCondition;

	WinningRank(Integer rank, String description, BiPredicate<GeneratedLotto, OfficialLotto> matchCondition) {
		this.rank = rank;
		this.description = description;
		this.matchCondition = matchCondition;
	}

	public static WinningRank from(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {
		return Arrays.stream(values())
			.filter(rank -> rank.matchCondition.test(generatedLotto, officialLotto))
			.findFirst()
			.orElse(NOTHING);
	}

	private static Integer getMatchCount(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {
		Set<Integer> winningNumbers = officialLotto.toNumberSet();
		return (int)generatedLotto.toNumberList()
			.stream()
			.filter(winningNumbers::contains)
			.count();
	}
}
