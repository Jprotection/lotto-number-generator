package boho.lottonumbergenerator.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.config.security.MemberDetails;
import boho.lottonumbergenerator.dto.MemberInfoRequest;
import boho.lottonumbergenerator.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/mypage")
	public String myPage(@AuthenticationPrincipal MemberDetails memberDetails, Model model) {
		MemberInfoRequest memberInfo = memberService.getMemberInfo(memberDetails.getUsername());
		model.addAttribute("memberInfo", memberInfo);
		return "my-page";
	}
}
