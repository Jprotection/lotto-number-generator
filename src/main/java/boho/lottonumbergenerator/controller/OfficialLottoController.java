package boho.lottonumbergenerator.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.dro.OfficialLottoSearchRequest;
import boho.lottonumbergenerator.service.LottoApiService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/official")
@RequiredArgsConstructor
public class OfficialLottoController {

	private final LottoApiService lottoApiService;

	@GetMapping
	public String findOfficialLotto(@ModelAttribute OfficialLottoSearchRequest request, Pageable pageable, Model model) {
		model.addAttribute("lotto", lottoApiService.findOfficialLotto(request, pageable));
		return "official_lotto";
	}
}
