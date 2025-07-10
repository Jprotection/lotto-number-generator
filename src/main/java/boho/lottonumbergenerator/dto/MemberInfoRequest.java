package boho.lottonumbergenerator.dto;

import java.time.LocalDateTime;

import boho.lottonumbergenerator.entity.member.AuthorityType;
import boho.lottonumbergenerator.entity.member.GenderType;
import boho.lottonumbergenerator.entity.member.Member;
import lombok.Builder;

@Builder
public record MemberInfoRequest(
	String username,
	GenderType gender,
	AuthorityType authority,
	LocalDateTime createDate,
	LocalDateTime lastLoginDate) {

	public static MemberInfoRequest of(Member member) {
		return MemberInfoRequest.builder()
			.username(member.getUsername())
			.gender(member.getGender())
			.authority(member.getAuthority())
			.createDate(member.getCreateDate())
			.lastLoginDate(member.getLastLoginDate())
			.build();
	}
}
