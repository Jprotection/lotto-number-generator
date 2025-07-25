package boho.lottonumbergenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import boho.lottonumbergenerator.domain.dto.AdminLottoGenerateRequest;
import boho.lottonumbergenerator.service.AdminService;
import boho.lottonumbergenerator.service.RoleService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	private final RoleService roleService;

	@GetMapping("/lotto")
	public String adminLottoView(@ModelAttribute("generateRequest") AdminLottoGenerateRequest request, Model model) {
		model.addAttribute("roles", roleService.getAllRoleDescriptions());
		return "adminpage/admin-page";
	}

	@PostMapping("/lotto")
	public String generateLottoByAdmin(@ModelAttribute @Validated AdminLottoGenerateRequest request) {
		adminService.lottoGenerateByAdmin(request);
		return "redirect:/admin/lotto";
	}
}
