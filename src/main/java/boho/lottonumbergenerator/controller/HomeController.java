package boho.lottonumbergenerator.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.dro.WinningLottoListResponse;
import boho.lottonumbergenerator.service.LottoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

	private final LottoService lottoService;

	@GetMapping
	public String findWinningLotto(Model model) {
		if (!lottoService.isOfficialLottoLoaded()) {
			model.addAttribute("message", "로또 데이터를 로딩 중입니다.");
			return "home";
		}

		List<WinningLottoListResponse> firstPrizeLotto = lottoService.findAllFirstPrizeLotto();
		List<WinningLottoListResponse> secondPrizeLotto = lottoService.findAllSecondPrizeLotto();
		List<WinningLottoListResponse> thirdPrizeLotto = lottoService.findAllThirdPrizeLotto();
		List<WinningLottoListResponse> fourthPrizeLotto = lottoService.findAllFourthPrizeLotto();
		List<WinningLottoListResponse> fifthPrizeLotto = lottoService.findAllFifthPrizeLotto();

		if (lottoService.isOfficialLottoLoaded()
			&& firstPrizeLotto.isEmpty()
			&& secondPrizeLotto.isEmpty()
			&& thirdPrizeLotto.isEmpty()
			&& fourthPrizeLotto.isEmpty()
			&& fifthPrizeLotto.isEmpty()) {
			model.addAttribute("message", "최신 회차의 당첨자가 없습니다.");
		}

		model.addAttribute("firstPrizeLotto", firstPrizeLotto);
		model.addAttribute("secondPrizeLotto", secondPrizeLotto);
		model.addAttribute("thirdPrizeLotto", thirdPrizeLotto);
		model.addAttribute("fourthPrizeLotto", fourthPrizeLotto);
		model.addAttribute("fifthPrizeLotto", fifthPrizeLotto);
		model.addAttribute("latestLotto", lottoService.findLatestOfficialLotto());

		return "home";
	}
}
