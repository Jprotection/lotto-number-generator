package boho.lottonumbergenerator.config.security;

import java.time.LocalDateTime;

import boho.lottonumbergenerator.entity.member.AuthorityType;
import boho.lottonumbergenerator.entity.member.GenderType;
import boho.lottonumbergenerator.entity.member.Member;
import boho.lottonumbergenerator.entity.member.StatusType;
import lombok.Builder;

@Builder
public record MemberLoginProcessingPrincipal(
	Long id,
	String username,
	String password,
	GenderType gender,
	AuthorityType authority,
	StatusType status,
	LocalDateTime createDate,
	LocalDateTime lastLoginDate,
	LocalDateTime withdrawDate) {

	public static MemberLoginProcessingPrincipal of(Member member) {
		return MemberLoginProcessingPrincipal.builder()
			.id(member.getId())
			.username(member.getUsername())
			.password(member.getPassword())
			.gender(member.getGender())
			.authority(member.getAuthority())
			.status(member.getStatus())
			.createDate(member.getCreateDate())
			.lastLoginDate(member.getLastLoginDate())
			.withdrawDate(member.getWithdrawDate())
			.build();
	}
}
