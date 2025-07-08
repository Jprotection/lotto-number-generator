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
import org.springframework.web.bind.annotation.RequestParam;

import boho.lottonumbergenerator.dto.ExcludeNumberRequest;
import boho.lottonumbergenerator.dto.IncludeNumberRequest;
import boho.lottonumbergenerator.service.LottoService;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class LottoController {

	private final LottoService lottoService;

	@PostMapping("/lotto")
	public String generateLotto(Model model,
		@RequestParam(defaultValue = "2") @Max(10) Integer count,
		@ModelAttribute @Validated IncludeNumberRequest includeNumberRequest,
		@ModelAttribute @Validated ExcludeNumberRequest excludeNumberRequest,
		@CurrentSecurityContext SecurityContext securityContext) {

		Authentication authentication = securityContext.getAuthentication();
		model.addAttribute("lotto",
			lottoService.generateLotto(count, includeNumberRequest, excludeNumberRequest, authentication));

		return "lotto";
	}

	@GetMapping("/lottos")
	public String getAllLotto(Model model) {
		model.addAttribute("lotto", lottoService.getAllGeneratedLotto());
		return "lotto";
	}
}
