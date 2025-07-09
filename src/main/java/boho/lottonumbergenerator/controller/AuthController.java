package boho.lottonumbergenerator.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import boho.lottonumbergenerator.config.security.UsernameDuplicateException;
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
	public String signupView(@ModelAttribute("registerRequest") MemberRegisterRequest request) {
		return "signup";
	}

	@GetMapping("/login")
	public String loginView(@SessionAttribute(value = "authError", required = false) String authError, Model model) {
		model.addAttribute("authError", authError);
		return "login";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute("registerRequest") @Validated MemberRegisterRequest request,
		BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "signup";
		}

		MemberRegisterRequest PasswordEncodedRequest =
			MemberRegisterRequest.changePasswordEncoded(request, passwordEncoder.encode(request.password()));

		try {
			authService.registerMember(PasswordEncodedRequest);
		} catch (UsernameDuplicateException e) {
			bindingResult.rejectValue("username", "duplicate", e.getMessage());
			return "signup";
		}

		return "redirect:/login?welcome";
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
