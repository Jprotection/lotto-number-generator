package boho.lottonumbergenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.service.LottoApiService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/official")
@RequiredArgsConstructor
public class OfficialLottoController {

	private final LottoApiService lottoApiService;

	@GetMapping
	public String getAllOfficialLotto(Model model) {
		model.addAttribute("lotto", lottoApiService.getAllOfficialLotto());
		return "official_lotto";
	}
}
