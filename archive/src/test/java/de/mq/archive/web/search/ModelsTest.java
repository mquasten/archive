package de.mq.archive.web.search;



import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import de.mq.archive.web.BasicI18NEnumModelImpl;
import de.mq.archive.web.OneWayStringMapping;

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
		
		
		
		final OneWayStringMapping<Locale, Enum<?>> i18n =  model.getI18NLabels();
		Assert.assertTrue(i18n instanceof BasicI18NEnumModelImpl);
		Assert.assertEquals(messageSource, ReflectionTestUtils.getField(i18n, MESSAGE_SOURCE_FIELD));
		
		
		
		
		@SuppressWarnings("unchecked")
		final Map<I18NSearchPageModelParts,Entry<String, IModel<String>>> models = (Map<I18NSearchPageModelParts, Entry<String, IModel<String>>>) ReflectionTestUtils.getField(i18n, "models");
	
		Assert.assertEquals(Stream.of(I18NSearchPageModelParts.values()).collect(Collectors.toSet()),models.keySet());
		models.keySet().forEach(key -> Assert.assertEquals(key.key, models.get(key).getKey()));
		models.keySet().forEach(key -> Assert.assertTrue(models.get(key).getValue() instanceof IModel));
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
