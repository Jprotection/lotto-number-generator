package boho.lottonumbergenerator.config.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MemberAccessDeniedHandler implements AccessDeniedHandler {

	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private final String errorPage;

	public MemberAccessDeniedHandler(String errorPage) {
		this.errorPage = errorPage;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {
		String deniedUrl = errorPage + "?error";
		request.getSession().setAttribute("accessError", accessDeniedException.getMessage());
		redirectStrategy.sendRedirect(request, response, deniedUrl);
	}
}
