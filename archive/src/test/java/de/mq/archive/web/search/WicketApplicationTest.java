package de.mq.archive.web.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.wicket.model.IModel;
import org.apache.wicket.proxy.ILazyInitProxy;
import org.apache.wicket.spring.SpringBeanLocator;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.WebApplicationContext;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.EnumModel;
import de.mq.archive.web.OneWayStringMapping;
import de.mq.archive.web.WicketApplication;

public class WicketApplicationTest {

	private final ServletContext ctx = Mockito.mock(ServletContext.class);
	private final WebApplicationContext webApplicationContext = Mockito.mock(WebApplicationContext.class);

	private final SearchPageController searchPageController = Mockito.mock(SearchPageController.class);

	@SuppressWarnings("unchecked")
	private final ActionListener<String> actionListener = Mockito.mock(ActionListener.class);

	@SuppressWarnings("rawtypes")
	private final OneWayStringMapping labels = Mockito.mock(OneWayStringMapping.class);
	@SuppressWarnings("unchecked")
	private final EnumModel<Archive> searchCriteriaWeb = Mockito.mock(EnumModel.class);
	@SuppressWarnings("unchecked")
	private final IModel<List<Archive>> listModel = Mockito.mock(IModel.class);

	private final SearchPageModelWeb searchPageModelWeb = Mockito.mock(SearchPageModelWeb.class);

	private WicketApplication wicketApplication = new WicketApplication();

	private Map<Class<?>, Object> beans = new HashMap<>();

	@SuppressWarnings("unchecked")
	@Before
	public final void setup() {

		Mockito.doAnswer(a -> new String[] { ((Class<?>) a.getArguments()[0]).getName() }).when(webApplicationContext).getBeanNamesForType(Mockito.any());
		Mockito.when(searchPageModelWeb.getI18NLabels()).thenReturn(labels);
		Mockito.when(searchPageModelWeb.getSearchCriteriaWeb()).thenReturn(searchCriteriaWeb);
		Mockito.when(searchPageModelWeb.getArchivesWeb()).thenReturn(listModel);
		Mockito.when(ctx.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)).thenReturn(webApplicationContext);
		@SuppressWarnings("rawtypes")
		final ArgumentCaptor<Class> clazzCaptor = ArgumentCaptor.forClass(Class.class);

		beans.put(SearchPageModelWeb.class, searchPageModelWeb);
		beans.put(SearchPageController.class, searchPageController);
		beans.put(ActionListener.class, actionListener);
		Mockito.doAnswer(a -> beans.get((Class<?>) a.getArguments()[1])).when(webApplicationContext).getBean(Mockito.anyString(), clazzCaptor.capture());

	}

	@Test
	public final void init() {

		final WicketTester tester = new WicketTester(wicketApplication, ctx);

		final SearchPage page = new SearchPage(null);

		tester.startPage(page);
		
		
		Assert.assertEquals(searchPageModelWeb, target(ReflectionTestUtils.getField(page, "searchPageModel")));
		Assert.assertEquals(searchPageController, target(ReflectionTestUtils.getField(page, "searchPageController")));
		Assert.assertEquals(actionListener, target(ReflectionTestUtils.getField(page, "actionListener")));

	}

	private Object target(final Object proxy) {
		return ((SpringBeanLocator) ((ILazyInitProxy) proxy).getObjectLocator()).locateProxyTarget();
	}

}