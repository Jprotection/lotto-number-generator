package boho.lottonumbergenerator.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import boho.lottonumbergenerator.dto.MemberRegisterRequest;
import boho.lottonumbergenerator.service.AuthService;
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

		return "redirect:/";
	}
}
