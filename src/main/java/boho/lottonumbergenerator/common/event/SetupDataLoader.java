package boho.lottonumbergenerator.common.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import boho.lottonumbergenerator.service.AuthService;
import boho.lottonumbergenerator.service.OfficialLottoService;
import boho.lottonumbergenerator.service.RoleService;
import boho.lottonumbergenerator.service.TitleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetupDataLoader {

	private final RoleService roleService;
	private final AuthService authService;
	private final TitleService titleService;
	private final OfficialLottoService officialLottoService;

	@EventListener(ApplicationReadyEvent.class)
	public void setupDataLoad() {

		roleService.createDefaultRolesIfNotFound();
		titleService.createDefaultTitleIfNotFound();
		authService.createAdminIfNotFound();
		officialLottoService.fetchAllOfficialLottoIfNotFound();
	}
}
