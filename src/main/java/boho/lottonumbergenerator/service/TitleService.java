package boho.lottonumbergenerator.service;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${title.default.name}")
	private String defaultTitleName;

	private final TitleRepository titleRepository;

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void initializeDefaultTitle() {

		if (!titleRepository.existsByIdIsNotNull()) {
			titleRepository.save(new Title(defaultTitleName));
			log.info("Default title added: [{}]", defaultTitleName);
		}

		log.info("Title list: {}", titleRepository.findAll().stream()
			.map(Title::getName)
			.toList());
	}

	@Transactional
	public void createTitle(Integer drawNumber, Integer prizeRank) {
		Title title = titleRepository.save(new Title(drawNumber + "회차 " + prizeRank + "등 당첨자"));
		log.info("New title created: [{}]", title.getName());
	}
}
