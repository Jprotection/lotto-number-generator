package boho.lottonumbergenerator.common.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import boho.lottonumbergenerator.domain.entity.member.Member;
import boho.lottonumbergenerator.domain.entity.member.MemberTitle;
import boho.lottonumbergenerator.domain.entity.member.Title;
import boho.lottonumbergenerator.repository.MemberTitleRepository;
import boho.lottonumbergenerator.service.TitleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberEventListener {

	private final TitleService titleService;
	private final MemberTitleRepository memberTitleRepository;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addDefaultTitleToNewMember(MemberRegisterSuccessEvent event) {
		Member member = (Member)event.getSource();
		Title defaultTitle = titleService.getDefaultTitle();

		memberTitleRepository.save(new MemberTitle(member, defaultTitle));
		log.info("[{}]님에게 [{}] 기본 칭호가 부여되었습니다.", member.getUsername(), defaultTitle.getName());
	}
}
