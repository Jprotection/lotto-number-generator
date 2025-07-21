package boho.lottonumbergenerator.service;

import boho.lottonumbergenerator.domain.entity.member.Title;

public interface TitleService {

	void createDefaultTitleIfNotFound();

	Title getDefaultTitle();
}
