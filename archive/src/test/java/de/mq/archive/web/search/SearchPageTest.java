package de.mq.archive.web.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.EnumModel;
import de.mq.archive.web.OneWayStringMapping;
import de.mq.archive.web.WicketApplication;

public class SearchPageTest {

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
	
	private Map<I18NSearchPageModelParts, String> paths = new HashMap<>();

	WicketTester tester;
	
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
		
		
		Arrays.stream(I18NSearchPageModelParts.values()).forEach( part -> Mockito.when(labels.part(part)).thenReturn(new Model<>(part.key()))) ;
		
		Mockito.when(searchCriteriaWeb.part(ArchiveModelParts.Name, String.class)).thenReturn(new Model<String>());	
		Mockito.when(searchCriteriaWeb.part(ArchiveModelParts.Category, Category.class)).thenReturn(new Model<Category>());	
		Mockito.when(searchCriteriaWeb.part(ArchiveModelParts.ArchiveId, String.class)).thenReturn(new Model<String>());	
		
		tester = new WicketTester(wicketApplication, ctx);
		final SearchPage page = new SearchPage(null);
		tester.startPage(page);
		
		paths.put(I18NSearchPageModelParts.SearchNameLabel, String.format("searchForm:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.SearchNameLabel.name())) );
		
		paths.put(I18NSearchPageModelParts.SearchCategoryLabel, String.format("searchForm:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.SearchCategoryLabel.name())) );
		paths.put(I18NSearchPageModelParts.SearchArchiveLabel, String.format("searchForm:%s",StringUtils.uncapitalize( I18NSearchPageModelParts.SearchArchiveLabel.name())) );
		paths.put(I18NSearchPageModelParts.SearchButton, String.format("searchForm:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.SearchButton.name())) );
		paths.put(I18NSearchPageModelParts.SearchCriteriaHeadline, StringUtils.uncapitalize(I18NSearchPageModelParts.SearchCriteriaHeadline.name())) ;
		paths.put(I18NSearchPageModelParts.ApplicationHeadline, StringUtils.uncapitalize(I18NSearchPageModelParts.ApplicationHeadline.name())) ;
		paths.put(I18NSearchPageModelParts.PageHeadline, StringUtils.uncapitalize(I18NSearchPageModelParts.PageHeadline.name())) ;
		
		paths.put(I18NSearchPageModelParts.NewButton, String.format("form:group:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.NewButton.name())) );
		paths.put(I18NSearchPageModelParts.ChangeButton, String.format("form:group:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.ChangeButton.name())) );
		paths.put(I18NSearchPageModelParts.ShowButton, String.format("form:group:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.ShowButton.name())) );
		paths.put(I18NSearchPageModelParts.SearchTableHeadline, String.format("form:group:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.SearchTableHeadline.name())) );
		paths.put(I18NSearchPageModelParts.NameHeader, String.format("form:group:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.NameHeader.name())) );
		paths.put(I18NSearchPageModelParts.CategoryHeader, String.format("form:group:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.CategoryHeader.name())) );
		paths.put(I18NSearchPageModelParts.DateHeader, String.format("form:group:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.DateHeader.name())) );
		paths.put(I18NSearchPageModelParts.ArchiveIdHeader, String.format("form:group:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.ArchiveIdHeader.name())) );
		
		
		
		
	}

	@Test
	public final void labels() {

		Arrays.stream(I18NSearchPageModelParts.values()).forEach( part -> Assert.assertEquals(part.key() ,tester.getComponentFromLastRenderedPage(paths.get(part)).getDefaultModel().getObject()));		

	}

	@Test
	public final void search() {
	
		final FormTester formTester = tester.newFormTester("searchForm");
		formTester.setValue("searchName", "kylie");
		Mockito.when(searchPageModelWeb.isSelected()).thenReturn(true);
	
		final List<Archive> rows = new ArrayList<>();
		Archive row = Mockito.mock(Archive.class);
		rows.add(row);
		Mockito.when(listModel.getObject()).thenReturn(rows);
		
		
		final Button showButton = (Button) tester.getComponentFromLastRenderedPage(paths.get(I18NSearchPageModelParts.ShowButton));
		final Button changeButton = (Button) tester.getComponentFromLastRenderedPage(paths.get(I18NSearchPageModelParts.ChangeButton));
		Assert.assertFalse(showButton.isEnabled());
		Assert.assertFalse(changeButton.isEnabled());
		@SuppressWarnings("unchecked")
		final EnumModel<Archive> currentRow = Mockito.mock(EnumModel.class);
		Mockito.when(searchPageController.newWebModel(row)).thenReturn(currentRow);
		Arrays.stream(ArchiveModelParts.values()).forEach(part -> Mockito.when(currentRow.part(part, String.class)).thenReturn(new Model<String>(part.name())));
		
		formTester.submit("searchButton");
	
		Mockito.verify(actionListener).process(SearchPageController.SEARCH_ACTION);
		Assert.assertTrue(showButton.isEnabled());
		Assert.assertTrue(changeButton.isEnabled());
		
		
		Arrays.stream(new ArchiveModelParts[] { ArchiveModelParts.Id,  ArchiveModelParts.ArchiveId, ArchiveModelParts.Name, ArchiveModelParts.Category, ArchiveModelParts.DocumentDate} ).forEach(part ->Assert.assertEquals( part.name(),  tester.getComponentFromLastRenderedPage(String.format("form:group:documents:0:%s" ,StringUtils.uncapitalize(part.name()))).getDefaultModel().getObject()));
		
	}

}
