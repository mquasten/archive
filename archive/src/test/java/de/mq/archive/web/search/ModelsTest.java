package de.mq.archive.web.search;



import java.util.Arrays;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.web.ActionListener;

public class ModelsTest {
	private static final String BEAN_FACTORY_FIELD = "beanFactory";

	private static final String ARCHIVE_SERVICE_FIELD = "archiveService";

	private static final String MESSAGE_SOURCE_FIELD = "messageSource";

	private final Models models = new Models();
	
	private final MessageSource messageSource = Mockito.mock(MessageSource.class); 
	private BeanFactory beanFactory = Mockito.mock(BeanFactory.class);
	private  ArchiveService archiveService = Mockito.mock(ArchiveService.class); 
	
	private SearchPageController searchPageController = Mockito.mock(SearchPageController.class);
	
	@Before
	public final void setup() {
		ReflectionTestUtils.setField(models, MESSAGE_SOURCE_FIELD, messageSource);
		ReflectionTestUtils.setField(models, BEAN_FACTORY_FIELD, beanFactory);
		ReflectionTestUtils.setField(models, ARCHIVE_SERVICE_FIELD, archiveService);
	}
	
	
	@Test
	public final void searchPageModel() {
		final SearchPageModelWeb model = models.searchPageModel();
		Assert.assertTrue(model instanceof SearchPageModelImpl);;
		
		Assert.assertNotNull(model.getSearchCriteriaWeb());
		Assert.assertTrue(model.getArchivesWeb() instanceof ListModel);
		Assert.assertNotNull(model.getSelectedArchiveWeb());
		Assert.assertNotNull(model.getSelectedArchiveWeb());
		Assert.assertTrue(model.getSearchCriteriaWeb().toDomain() instanceof ArchiveImpl);
		
		Arrays.stream(ArchiveModelParts.values()).forEach(part ->  Assert.assertTrue(model.getSearchCriteriaWeb().part(part, Object.class) instanceof IModel ));
	}
	
	@Test
	public final void  i18NSearchPageModelParts() {
		final I18NSearchPageModel model =  models.i18NSearchPageModel();
		Assert.assertTrue(model instanceof I18NSearchPageModel);
		Assert.assertEquals(messageSource, ReflectionTestUtils.getField(model, MESSAGE_SOURCE_FIELD));
	}
	
	
	@Test
	public final void searchPageController() {
		final SearchPageController model = models.searchPageController();
		Assert.assertTrue(model instanceof SearchPageController);
		Assert.assertEquals(archiveService, ReflectionTestUtils.getField(model, ARCHIVE_SERVICE_FIELD));
	}
	
	@Test
	public final void searchActionListener() {
		Mockito.when(beanFactory.getBean(SearchPageController.class)).thenReturn(searchPageController);
		final ActionListener actionListener = models.searchActionListener();
		Assert.assertEquals(beanFactory, ReflectionTestUtils.getField(actionListener, BEAN_FACTORY_FIELD));
		Mockito.verify(beanFactory, Mockito.times(1)).getBean(SearchPageController.class);
	}

}
