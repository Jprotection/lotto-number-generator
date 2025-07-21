package boho.lottonumbergenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.domain.dto.WinningLottoRankGroupResponse;
import boho.lottonumbergenerator.service.GeneratedLottoService;
import boho.lottonumbergenerator.service.OfficialLottoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

	private final OfficialLottoService officialLottoService;
	private final GeneratedLottoService generatedLottoService;

	@GetMapping
	public String findWinningLotto(Model model) {
		if (officialLottoService.isOfficialLottoNotLoaded()) {
			model.addAttribute("message", "로또 데이터를 로딩 중입니다.");
			return "home";
		}

		WinningLottoRankGroupResponse winningLotto = generatedLottoService.fetchAllWinningLotto();
		if (winningLotto.isEmptyAll()) {
			model.addAttribute("message", "최신 회차의 당첨자가 없습니다.");
		}

		model.addAttribute("winningLotto", winningLotto);
		model.addAttribute("latestLotto", officialLottoService.getLatestOfficialLotto());

		return "home";
	}
}
