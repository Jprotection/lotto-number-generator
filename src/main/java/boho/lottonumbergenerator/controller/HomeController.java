package boho.lottonumbergenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.service.LottoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

	private final LottoService lottoService;

	@GetMapping
	public String findWinningLotto(Model model) {
		model.addAttribute("winningLotto", lottoService.getAllWinningLotto());
		return "home";
	}
}
