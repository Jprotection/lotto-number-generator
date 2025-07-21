package boho.lottonumbergenerator.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.domain.dto.LottoGenerateRequest;
import boho.lottonumbergenerator.service.GeneratedLottoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class LottoController {

	private final GeneratedLottoService generatedLottoService;

	@PostMapping("/lotto")
	public String generateLotto(
		@ModelAttribute @Validated LottoGenerateRequest request,
		@CurrentSecurityContext SecurityContext securityContext,
		Model model) {

		Authentication authentication = securityContext.getAuthentication();
		model.addAttribute("lotto",
			generatedLottoService.generateLotto(request, authentication));

		return "lotto";
	}

	@GetMapping("/lottos")
	public String getAllLotto(Model model) {
		model.addAttribute("lotto", generatedLottoService.getAllGeneratedLotto());
		return "lotto";
	}
}
