package de.mq.archive.web.search;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.mq.archive.web.BasicI18NEnumModelImpl;
import de.mq.archive.web.OneWayStringMapping;

@Configuration
class Models {
	@Inject
	private  MessageSource messageSource; 
	
	 @Bean(name="archiveSearchCriteria")
	 @Scope("request")
	 ArchiveModel searchCriteria() {
		return new ArchiveModelImpl();
	 }
	 
	 @Bean(name="i18NSearchPageModelParts")
	 @Scope("session")
	 OneWayStringMapping<Locale, Enum<?>> i18NSearchPageModelParts() {
		return new BasicI18NEnumModelImpl(messageSource, Arrays.asList(I18NSearchPageModelParts.values()), Arrays.asList(I18NSearchPageModelParts.values()).stream().map( part -> part.key).collect(Collectors.toList()));
		 
	 }
	 
	 

}


