package de.mq.archive.web.search;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.mq.archive.domain.ArchiveService;

@Configuration
class Models {
	@Inject
	private  MessageSource messageSource; 
	
	@Inject
	private  ArchiveService archiveService; 
	
	 @Bean()
	 @Scope("request")
	 ArchiveModel searchCriteria() {
		return new ArchiveModelImpl();
	 }
	 
	 @Bean()
	 @Scope("session")
	 I18NSearchPageModel i18NSearchPageModelParts() {
		return new I18NSearchPageModelImpl(messageSource);
		 
	 }
	 
	 @Bean()
	 @Scope("singleton")
	 SearchPageController searchPageController() {
		return new SearchPageControllerImpl(archiveService);
		 
	 }
	 
	 

}


