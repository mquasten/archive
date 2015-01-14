package de.mq.archive.web.search;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.BasicEnumModelImpl;
import de.mq.archive.web.BasicI18NEnumModelImpl;
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
	 SearchPageModelImpl searchPageModel() {
		 return new SearchPageModelImpl(new BasicEnumModelImpl<Archive>(Arrays.asList(ArchiveModelParts.values()), ArchiveImpl.class), new BasicI18NEnumModelImpl(messageSource,  Arrays.asList(I18NSearchPageModelParts.values()), Arrays.asList(I18NSearchPageModelParts.values()).stream().map( part -> part.key()).collect(Collectors.toList())), new ListModel<>(), new Model<>(), new Model<>(Integer.MAX_VALUE) );
	 }
	 
	 @Bean()
	 @Scope("singleton")
	 SearchPageController searchPageController() {
		return new SearchPageControllerImpl(archiveService);
		 
	 }
	 
	 @Bean(name="searchActionListener")
	 @Scope("singleton")
	 public ActionListener<?> searchActionListener() {
		 return new SimpleParameterInjectionActionListenerImpl(beanFactory, SearchPageController.class);
	 }
	 

}


