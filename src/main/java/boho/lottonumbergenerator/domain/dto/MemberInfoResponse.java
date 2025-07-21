package boho.lottonumbergenerator.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import boho.lottonumbergenerator.domain.entity.member.GenderType;
import lombok.Builder;

@Builder
public record MemberInfoResponse(
	String username,
	GenderType gender,
	List<String> roles,
	LocalDateTime createDate,
	LocalDateTime lastLoginDate,
	List<String> titles) {
}
