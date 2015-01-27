package de.mq.archive.web.edit;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.web.BasicEnumModelImpl;
import de.mq.archive.web.BasicI18NEnumModelImpl;
import de.mq.archive.web.search.ArchiveModelParts;

@Configuration
class EditPageModels {

	@Autowired
	private MessageSource messageSource;

	@Bean()
	@Scope("session")
	EditPageModelImpl editPageModel() {
		return new EditPageModelImpl(new BasicEnumModelImpl<Archive>(Arrays.asList(ArchiveModelParts.values()), ArchiveImpl.class), new BasicI18NEnumModelImpl(messageSource, Arrays.asList(I18NEditPageModelParts.values()), Arrays.asList(I18NEditPageModelParts.values()).stream().map(part -> part.key()).collect(Collectors.toList())));
	}

}
