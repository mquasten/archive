package de.mq.archive.web.search;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;

import de.mq.archive.web.BasicI18NEnumModelImpl;

public class I18NSearchPageModelImpl extends BasicI18NEnumModelImpl implements I18NSearchPageModel{

	public I18NSearchPageModelImpl(MessageSource messageSource) {
		super(messageSource,  Arrays.asList(I18NSearchPageModelParts.values()), Arrays.asList(I18NSearchPageModelParts.values()).stream().map( part -> part.key).collect(Collectors.toList()));
	}

	private static final long serialVersionUID = 1L;

}
