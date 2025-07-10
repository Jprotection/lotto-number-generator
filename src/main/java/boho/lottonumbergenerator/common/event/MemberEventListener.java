package boho.lottonumbergenerator.common.event;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import boho.lottonumbergenerator.entity.member.Member;
import boho.lottonumbergenerator.entity.member.MemberTitle;
import boho.lottonumbergenerator.entity.member.Title;
import boho.lottonumbergenerator.repository.MemberTitleRepository;
import boho.lottonumbergenerator.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberEventListener {

	@Value("${title.default.name}")
	private String defaultTitleName;

	private final TitleRepository titleRepository;
	private final MemberTitleRepository memberTitleRepository;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addDefaultTitleToNewMember(MemberRegisterSuccessEvent event) {
		Member member = (Member)event.getSource();
		Title defaultTitle = titleRepository.findByName(defaultTitleName)
			.orElseThrow(() -> new NoSuchElementException(defaultTitleName + "은(는) 존재하지 않는 칭호입니다."));

		MemberTitle memberTitle = MemberTitle.builder()
			.member(member)
			.title(defaultTitle)
			.build();

		memberTitleRepository.save(memberTitle);

		log.info("[{}]님에게 [{}] 기본 칭호가 부여되었습니다.", member.getUsername(), defaultTitle.getName());
	}
}
