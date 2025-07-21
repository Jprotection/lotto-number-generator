package boho.lottonumbergenerator.domain.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record LottoGenerateRequest(

	@Min(1) @Max(10)
	Integer count,

	@Min(1) @Max(45)
	Integer firstIncludeNumber,

	@Min(1) @Max(45)
	Integer secondIncludeNumber,

	@Min(1) @Max(45)
	Integer thirdIncludeNumber,

	@Min(1) @Max(45)
	Integer fourthIncludeNumber,

	@Min(1) @Max(45)
	Integer fifthIncludeNumber,

	@Min(1) @Max(45)
	Integer firstExcludeNumber,

	@Min(1) @Max(45)
	Integer secondExcludeNumber,

	@Min(1) @Max(45)
	Integer thirdExcludeNumber,

	@Min(1) @Max(45)
	Integer fourthExcludeNumber,

	@Min(1) @Max(45)
	Integer fifthExcludeNumber,

	@Min(1) @Max(45)
	Integer sixthExcludeNumber,

	@Min(1) @Max(45)
	Integer seventhExcludeNumber,

	@Min(1) @Max(45)
	Integer eighthExcludeNumber,

	@Min(1) @Max(45)
	Integer ninthExcludeNumber,

	@Min(1) @Max(45)
	Integer tenthExcludeNumber) {

	public List<Integer> getIncludeNumbers() {
		return Stream.of(
				firstIncludeNumber,
				secondIncludeNumber,
				thirdIncludeNumber,
				fourthIncludeNumber,
				fifthIncludeNumber)
			.filter(Objects::nonNull)
			.sorted()
			.toList();
	}

	public List<Integer> getExcludeNumbers() {
		return Stream.of(
				firstExcludeNumber,
				secondExcludeNumber,
				thirdExcludeNumber,
				fourthExcludeNumber,
				fifthExcludeNumber,
				sixthExcludeNumber,
				seventhExcludeNumber,
				eighthExcludeNumber,
				ninthExcludeNumber,
				tenthExcludeNumber)
			.filter(Objects::nonNull)
			.sorted()
			.toList();
	}
}
