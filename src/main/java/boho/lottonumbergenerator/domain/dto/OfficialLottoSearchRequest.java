package boho.lottonumbergenerator.domain.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public record OfficialLottoSearchRequest(
	Integer startDrawNumber,
	Integer endDrawNumber,

	// html lang=ko 일 때 input type date th:field 매핑을 위해서
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate startDrawDate,

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate endDrawDate,

	Integer firstIncludeNumber,
	Integer secondIncludeNumber,
	Integer thirdIncludeNumber,
	Integer bonusNumber
) {
}
