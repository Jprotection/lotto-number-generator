package boho.lottonumbergenerator.config.security;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MemberAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

		String errorMessage;

		if (exception instanceof UsernameNotFoundException) {
			errorMessage = "존재하지 않는 아이디입니다.";
		} else if (exception instanceof BadCredentialsException) {
 			errorMessage = "잘못된 비밀번호입니다.";
		} else if (exception instanceof DisabledException) {
			errorMessage = "탈퇴 처리된 계정입니다.";
		} else {
			errorMessage = "유효하지 않은 요청입니다";
		}

		request.getSession().setAttribute("authError", errorMessage);
		setDefaultFailureUrl("/login?error");
		super.onAuthenticationFailure(request, response, exception);
	}
}
