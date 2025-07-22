package boho.lottonumbergenerator.common.event;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import boho.lottonumbergenerator.domain.dto.WinningLottoListResponse;
import boho.lottonumbergenerator.domain.entity.lotto.WinningRank;
import boho.lottonumbergenerator.domain.entity.member.Member;
import boho.lottonumbergenerator.domain.entity.member.MemberTitle;
import boho.lottonumbergenerator.domain.entity.member.Title;
import boho.lottonumbergenerator.repository.MemberTitleRepository;
import boho.lottonumbergenerator.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LottoEventListener {

	private final TitleRepository titleRepository;
	private final MemberTitleRepository memberTitleRepository;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void awardTitlesToWinningMembers(WinningLottoDetectedEvent event) {
		log.info("Detected winning lotto event!");
		Map<WinningRank, List<WinningLottoListResponse>> winningLotto = event.getWinningLotto();

		// 등수별로 반복
		winningLotto.entrySet()
			.stream()
			.filter(entry -> entry.getKey() != WinningRank.NOTHING && !entry.getValue().isEmpty())
			.forEach(entry -> {
				List<WinningLottoListResponse> responses = entry.getValue();

				// member 기준으로 중복 제거
				Map<Member, WinningLottoListResponse> uniqueByMember = responses
					.stream()
					.collect(Collectors.toMap(WinningLottoListResponse::member, response -> response,
						(existing, replacement) -> existing));

				// 각 member 에게 등수별 칭호 생성 및 저장
				uniqueByMember.forEach((member, response) -> {
					Title title = titleRepository.findByName(generateTitleNameFrom(response))
						.orElseGet(() -> {
							Title created = titleRepository.save(new Title(generateTitleNameFrom(response)));
							log.info("New [{}] title created", created.getName());
							return created;
						});
					memberTitleRepository.save(new MemberTitle(member, title));
					log.info("[{}] title has been assigned to [{}] member", title.getName(), member.getUsername());
				});
			});
	}

	private String generateTitleNameFrom(WinningLottoListResponse response) {
		return response.drawDate() + " " + response.drawNumber() + "회차 " + response.prizeRank() + "등 당첨자";
	}
}
