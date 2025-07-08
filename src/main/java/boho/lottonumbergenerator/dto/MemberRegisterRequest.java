package boho.lottonumbergenerator.dto;

import boho.lottonumbergenerator.entity.member.GenderType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record MemberRegisterRequest(

	@Size(max = 8, message = "아이디는 최대 8자입니다.")
	String username,

	@Size(min = 8, message = "비밀번호는 최소 8자입니다.")
	String password,

	@NotNull(message = "성별은 반드시 선택해야 합니다.")
	GenderType gender) {

	public static MemberRegisterRequest changePasswordEncoded(MemberRegisterRequest request, String encodedPassword) {
		return MemberRegisterRequest.builder()
			.username(request.username)
			.password(encodedPassword)
			.gender(request.gender)
			.build();
	}
}
