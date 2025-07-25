package boho.lottonumbergenerator.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record AdminLottoGenerateRequest(
	String roleDescription,
	LottoRandomOption randomOption,

	@Min(5) @Max(500)
	Integer count
) {
}
