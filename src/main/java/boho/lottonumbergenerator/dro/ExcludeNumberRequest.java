package boho.lottonumbergenerator.dro;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ExcludeNumberRequest(

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

	public Set<Integer> toExcludeNumberSet() {
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
			.collect(Collectors.toSet());
	}
}
