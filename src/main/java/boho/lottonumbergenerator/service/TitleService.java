package boho.lottonumbergenerator.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boho.lottonumbergenerator.entity.member.Title;
import boho.lottonumbergenerator.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TitleService {

	private final TitleRepository titleRepository;

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void initializeDefaultTitle() {

		if (!titleRepository.existsByIdIsNotNull()) {
			titleRepository.save(new Title("로또 탐구자"));
			log.info("기본 칭호 추가: 로또 탐구자");
		}
	}
}
