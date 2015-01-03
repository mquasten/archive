package de.mq.archive.web.search;

import javax.inject.Inject;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.mq.archive.domain.ArchiveService;
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.SimpleParameterInjectionActionListenerImpl;

@Configuration
class Models {
	@Inject
	private  MessageSource messageSource; 
	
	@Inject
	private  ArchiveService archiveService; 
	@Inject
	private BeanFactory beanFactory;
	
	 @Bean()
	 @Scope("session")
	 SearchPageModel searchCriteria() {
		return new SearchPageModelImpl();
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
	 
	 @Bean(name="searchActionListener")
	 @Scope("singleton")
	 public ActionListener searchActionListener() {
		 return new SimpleParameterInjectionActionListenerImpl(beanFactory, SearchPageController.class);
	 }
	 

}


