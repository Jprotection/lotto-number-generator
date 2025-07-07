package boho.lottonumbergenerator.dto;

import boho.lottonumbergenerator.entity.member.GenderType;
import lombok.Builder;

@Builder
public record MemberRegisterRequest(
	String username,
	String password,
	GenderType gender) {

	public static MemberRegisterRequest changePasswordEncoded(MemberRegisterRequest request, String encodedPassword) {
		return MemberRegisterRequest.builder()
			.username(request.username)
			.password(encodedPassword)
			.gender(request.gender)
			.build();
	}
}
