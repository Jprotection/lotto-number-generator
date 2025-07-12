package boho.lottonumbergenerator.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.dto.OfficialLottoSearchRequest;
import boho.lottonumbergenerator.service.LottoApiService;
import boho.lottonumbergenerator.service.LottoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/official")
@RequiredArgsConstructor
public class OfficialLottoController {

	private final LottoApiService lottoApiService;
	private final LottoService lottoService;

	@GetMapping
	public String findOfficialLotto(
		@ModelAttribute("searchRequest") OfficialLottoSearchRequest request,
		@PageableDefault(sort = "drawNumber", direction = Sort.Direction.DESC) Pageable pageable,
		Model model) {
		model.addAttribute("latestLotto", lottoService.getLatestOfficialLottoInfo());
		model.addAttribute("pageable", pageable);
		model.addAttribute("lotto", lottoApiService.findOfficialLotto(request, pageable));
		return "official-lotto";
	}
}
