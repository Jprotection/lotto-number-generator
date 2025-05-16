package boho.lottonumbergenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.service.LottoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class LottoController {

	private final LottoService lottoService;

	@GetMapping("/lotto")
	public String generateLotto(Model model) {
		model.addAttribute("lotto", lottoService.generateLotto());
		return "lotto";
	}

	@GetMapping("/lottos")
	public String getAllLotto(Model model) {
		model.addAttribute("lotto", lottoService.getAllGeneratedLotto());
		return "lotto";
	}
}
