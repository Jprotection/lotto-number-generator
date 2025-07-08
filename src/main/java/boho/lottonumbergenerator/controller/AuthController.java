package boho.lottonumbergenerator.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import boho.lottonumbergenerator.dto.MemberRegisterRequest;
import boho.lottonumbergenerator.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final PasswordEncoder passwordEncoder;
	private final AuthService authService;

	@GetMapping("/signup")
	public String signupView() {
		return "signup";
	}

	@GetMapping("/login")
	public String loginView() {
		return "login";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute MemberRegisterRequest request) {
		MemberRegisterRequest PasswordEncodedRequest =
			MemberRegisterRequest.changePasswordEncoded(request, passwordEncoder.encode(request.password()));
		authService.registerMember(PasswordEncodedRequest);

		return "redirect:/login";
	}

	@GetMapping("/logout")
	public String logout(
		HttpServletRequest request, HttpServletResponse response,
		@CurrentSecurityContext SecurityContext securityContext) {
		// Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}

		return "redirect:/login";
	}
}
