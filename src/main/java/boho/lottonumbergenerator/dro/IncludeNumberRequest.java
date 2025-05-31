package boho.lottonumbergenerator.dro;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record IncludeNumberRequest(

	@Min(1) @Max(45)
	Integer firstIncludeNumber,

	@Min(1) @Max(45)
	Integer secondIncludeNumber,

	@Min(1) @Max(45)
	Integer thirdIncludeNumber,

	@Min(1) @Max(45)
	Integer fourthIncludeNumber,

	@Min(1) @Max(45)
	Integer fifthIncludeNumber) {

	public Set<Integer> toIncludeNumberSet() {
		return Stream.of(
				firstIncludeNumber,
				secondIncludeNumber,
				thirdIncludeNumber,
				fourthIncludeNumber,
				fifthIncludeNumber
			)
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());
	}

	public List<Integer> toIncludeNumberList() {
		return toIncludeNumberSet()
			.stream()
			.sorted()
			.toList();
	}
}
