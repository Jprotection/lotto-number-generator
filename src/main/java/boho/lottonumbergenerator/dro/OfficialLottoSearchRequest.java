package boho.lottonumbergenerator.dro;

import java.time.LocalDate;

public record OfficialLottoSearchRequest(
	Long startDrawNumber,
	Long endDrawNumber,
	LocalDate startDrawDate,
	LocalDate endDrawDate,
	Integer firstIncludeNumber,
	Integer secondIncludeNumber,
	Integer thirdIncludeNumber,
	Integer bonusNumber
) {
}
