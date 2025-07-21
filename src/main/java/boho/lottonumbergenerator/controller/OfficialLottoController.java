package boho.lottonumbergenerator.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.domain.dto.OfficialLottoSearchRequest;
import boho.lottonumbergenerator.service.OfficialLottoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/official")
@RequiredArgsConstructor
public class OfficialLottoController {

	private final OfficialLottoService officialLottoService;

	@GetMapping
	public String findOfficialLotto(
		@ModelAttribute("searchRequest") OfficialLottoSearchRequest request,
		@PageableDefault(sort = "drawNumber", direction = Sort.Direction.DESC) Pageable pageable,
		Model model) {
		model.addAttribute("latestLotto", officialLottoService.getLatestOfficialLotto());
		model.addAttribute("pageable", pageable);
		model.addAttribute("lotto", officialLottoService.findOfficialLottoWithPaging(request, pageable));
		return "official-lotto";
	}
}
