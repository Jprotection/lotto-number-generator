package boho.lottonumbergenerator.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.config.security.MemberDetails;
import boho.lottonumbergenerator.dto.MemberInfoResponse;
import boho.lottonumbergenerator.dto.MemberLottoSearchRequest;
import boho.lottonumbergenerator.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/mypage")
	public String myPage(@AuthenticationPrincipal MemberDetails memberDetails, Model model) {
		MemberInfoResponse memberInfo = memberService.getMemberInfo(memberDetails.getUsername());
		model.addAttribute("memberInfo", memberInfo);
		return "mypage/my-page";
	}

	@GetMapping("/lottos/{id}")
	public String myLotto(@PathVariable Long id,
		@ModelAttribute("searchRequest") MemberLottoSearchRequest request,
		@PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
		@CurrentSecurityContext SecurityContext securityContext,
		Model model) {
		if (!isMemberIdMatching(id, securityContext)) {
			throw new AccessDeniedException("유효하지 않은 접근입니다!");
		}

		model.addAttribute("memberLotto", memberService.findMemberLotto(id, request, pageable));
		model.addAttribute("pageable", pageable);
		return "mypage/my-lotto";
	}

	@GetMapping("/goodbye")
	public String goodbyeView() {

		return "mypage/goodbye";
	}

	@DeleteMapping("/{id}")
	public String withdrawMember(HttpServletRequest request, HttpServletResponse response,
		@PathVariable Long id, @CurrentSecurityContext SecurityContext securityContext) {
		if (!isMemberIdMatching(id, securityContext)) {
			throw new AccessDeniedException("유효하지 않은 접근입니다!");
		}

		memberService.withdrawMember(id);
		new SecurityContextLogoutHandler().logout(request, response, securityContext.getAuthentication());

		return "redirect:/login?goodbye";
	}

	// PathVariable로 넘어오는 Member Id와 Security Context의 Authentication에 담긴 MemberDetails의 Id가 일치하는지 확인
	private boolean isMemberIdMatching(Long id, SecurityContext securityContext) {
		Authentication authentication = securityContext.getAuthentication();
		MemberDetails memberDetails = (MemberDetails)authentication.getPrincipal();

		return id.equals(memberDetails.getId());
	}
}
