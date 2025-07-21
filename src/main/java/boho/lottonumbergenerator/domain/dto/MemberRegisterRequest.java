package boho.lottonumbergenerator.domain.dto;

import boho.lottonumbergenerator.domain.entity.member.GenderType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record MemberRegisterRequest(

	@Size(min = 4, max = 8, message = "아이디는 4자 이상 8자 아하입니다.")
	String username,

	@Size(min = 8, max = 12, message = "비밀번호는 8자 이상 12자 이하입니다.")
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
