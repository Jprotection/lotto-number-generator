package boho.lottonumbergenerator.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import boho.lottonumbergenerator.config.security.MemberDetails;
import boho.lottonumbergenerator.config.security.UsernameDuplicateException;
import boho.lottonumbergenerator.dto.MemberRegisterRequest;
import boho.lottonumbergenerator.service.AuthService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final PasswordEncoder passwordEncoder;
	private final AuthService authService;

	/* 회원가입 view 호출 */
	@GetMapping("/signup")
	public String signupView(@ModelAttribute("registerRequest") MemberRegisterRequest request) {
		return "signup";
	}

	/* 로그인 view 호출 */
	@GetMapping("/login")
	public String loginView(@SessionAttribute(value = "authError", required = false) String authError, Model model) {
		model.addAttribute("authError", authError);
		return "login";
	}

	/* 로그아웃 view 호출 */
	@GetMapping("/logout")
	public String logoutView() {
		return "logout";
	}

	/* 회원가입 실행 */
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

	/* 로그인은 했지만 접근 권한이 없을 때 호출 */
	@GetMapping("/denied")
	public String accessDenied(@SessionAttribute(value = "accessError", required = false) String accessError,
		@AuthenticationPrincipal MemberDetails memberDetails, Model model) {

		model.addAttribute("username", memberDetails.getUsername());
		model.addAttribute("accessError", accessError);

		return "denied";
	}
}
