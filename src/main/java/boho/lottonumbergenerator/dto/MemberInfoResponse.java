package boho.lottonumbergenerator.dto;

import java.time.LocalDateTime;
import java.util.List;

import boho.lottonumbergenerator.entity.member.AuthorityType;
import boho.lottonumbergenerator.entity.member.GenderType;
import lombok.Builder;

@Builder
public record MemberInfoResponse(
	String username,
	GenderType gender,
	AuthorityType authority,
	LocalDateTime createDate,
	LocalDateTime lastLoginDate,
	List<String> titles) {
}
