package boho.lottonumbergenerator.dto;

public record MemberLottoSearchRequest(
	Integer prizeRank,
	Integer firstIncludeNumber,
	Integer secondIncludeNumber,
	Integer thirdIncludeNumber
) {
}
