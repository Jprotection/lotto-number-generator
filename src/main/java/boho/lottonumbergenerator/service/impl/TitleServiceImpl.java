package boho.lottonumbergenerator.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boho.lottonumbergenerator.domain.entity.member.Title;
import boho.lottonumbergenerator.repository.TitleRepository;
import boho.lottonumbergenerator.service.TitleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TitleServiceImpl implements TitleService {

	@Value("${title.default.name}")
	private String defaultTitleName;
	private final TitleRepository titleRepository;

	@Override
	@Transactional
	public void createDefaultTitleIfNotFound() {
		titleRepository.findByName(defaultTitleName)
			.ifPresentOrElse(this::logTitleAlreadyExists,
				() -> logTitleCreated(titleRepository.save(new Title(defaultTitleName))));
	}

	@Override
	public Title getDefaultTitle() {
		return titleRepository.findByName(defaultTitleName)
			.orElseThrow(
				() -> new EntityNotFoundException("[" + defaultTitleName + "] default title not found"));
	}

	private void logTitleAlreadyExists(Title title) {
		log.info("[{}] title already exists", title.getName());
	}

	private void logTitleCreated(Title title) {
		log.info("[{}] title created", title.getName());
	}
}
