package boho.lottonumbergenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.dro.ExcludeNumberRequest;
import boho.lottonumbergenerator.dro.IncludeNumberRequest;
import boho.lottonumbergenerator.service.LottoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class LottoController {

	private final LottoService lottoService;

	@PostMapping("/lotto")
	public String generateLotto(Model model,
		@ModelAttribute @Validated IncludeNumberRequest includeNumberRequest,
		@ModelAttribute @Validated ExcludeNumberRequest excludeNumberRequest) {
		model.addAttribute("lotto",
			lottoService.generateLotto(includeNumberRequest, excludeNumberRequest));
		return "lotto";
	}

	@GetMapping("/lottos")
	public String getAllLotto(Model model) {
		model.addAttribute("lotto", lottoService.getAllGeneratedLotto());
		return "lotto";
	}
}
