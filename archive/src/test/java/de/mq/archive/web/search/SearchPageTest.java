package de.mq.archive.web.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
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
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.ComponentFactory;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TestConstants;
import de.mq.archive.web.TwoWayMapping;
import de.mq.archive.web.WicketApplication;
import de.mq.archive.web.edit.EditPage;
import de.mq.archive.web.edit.EditPageModelWeb;

public class SearchPageTest {

	private final ServletContext ctx = Mockito.mock(ServletContext.class);
	private final WebApplicationContext webApplicationContext = Mockito.mock(WebApplicationContext.class);

	private final SearchPageController searchPageController = Mockito.mock(SearchPageController.class);

	@SuppressWarnings("unchecked")
	private final ActionListener<String> actionListener = Mockito.mock(ActionListener.class);

	@SuppressWarnings("rawtypes")
	private final OneWayMapping labels = Mockito.mock(OneWayMapping.class);
	@SuppressWarnings("unchecked")
	private final TwoWayMapping<Archive,Enum<?>> searchCriteriaWeb = Mockito.mock(TwoWayMapping.class);
	@SuppressWarnings("unchecked")
	private final IModel<List<Archive>> listModel = Mockito.mock(IModel.class);

	private final SearchPageModelWeb searchPageModelWeb = Mockito.mock(SearchPageModelWeb.class);

	private WicketApplication wicketApplication = new WicketApplication();

	private Map<Class<?>, Object> beans = new HashMap<>();

	private Map<I18NSearchPageModelParts, String> paths = new HashMap<>();

	@SuppressWarnings("unchecked")
	private IModel<String> selectedArchive = Mockito.mock(IModel.class);

	private EditPageModelWeb editPageModelWeb = Mockito.mock(EditPageModelWeb.class);
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
		beans.put(ComponentFactory.class, TestConstants.COMPONENT_FACTORY);
		beans.put(EditPageModelWeb.class, editPageModelWeb);
		
		Mockito.doAnswer(a -> beans.get((Class<?>) a.getArguments()[1])).when(webApplicationContext).getBean(Mockito.anyString(), clazzCaptor.capture());

		Mockito.when(searchPageModelWeb.getSelectedArchiveWeb()).thenReturn(selectedArchive);
		Arrays.stream(I18NSearchPageModelParts.values()).forEach(part -> Mockito.when(labels.part(part)).thenReturn(new Model<>(part.key())));

		Mockito.when(searchCriteriaWeb.part(ArchiveModelParts.Name)).thenReturn(new Model<>());
		Mockito.when(searchCriteriaWeb.part(ArchiveModelParts.Category)).thenReturn(new Model<>());
		Mockito.when(searchCriteriaWeb.part(ArchiveModelParts.ArchiveId)).thenReturn(new Model<>());

		tester = new WicketTester(wicketApplication, ctx);
		
		beans.put(IGenericComponent.class, new FileUploadField("fileUpload"));
		final SearchPage page = new SearchPage(null);
		tester.startPage(page);

		paths.put(I18NSearchPageModelParts.SearchNameLabel, String.format("searchForm:%s", I18NSearchPageModelParts.SearchNameLabel.wicketId()));

		paths.put(I18NSearchPageModelParts.SearchCategoryLabel, String.format("searchForm:%s", I18NSearchPageModelParts.SearchCategoryLabel.wicketId()));
		paths.put(I18NSearchPageModelParts.SearchArchiveLabel, String.format("searchForm:%s", StringUtils.uncapitalize(I18NSearchPageModelParts.SearchArchiveLabel.name())));
		paths.put(I18NSearchPageModelParts.SearchButton, String.format("searchForm:%s", I18NSearchPageModelParts.SearchButton.wicketId()));
		paths.put(I18NSearchPageModelParts.SearchCriteriaHeadline, I18NSearchPageModelParts.SearchCriteriaHeadline.wicketId());
		paths.put(I18NSearchPageModelParts.ApplicationHeadline, I18NSearchPageModelParts.ApplicationHeadline.wicketId());
		paths.put(I18NSearchPageModelParts.PageHeadline, I18NSearchPageModelParts.PageHeadline.wicketId());

		paths.put(I18NSearchPageModelParts.NewButton, String.format("form:group:%s", I18NSearchPageModelParts.NewButton.wicketId()));
		paths.put(I18NSearchPageModelParts.ChangeButton, String.format("form:group:%s", I18NSearchPageModelParts.ChangeButton.wicketId()));
		paths.put(I18NSearchPageModelParts.ShowButton, String.format("form:group:%s", I18NSearchPageModelParts.ShowButton.wicketId()));
		paths.put(I18NSearchPageModelParts.SearchTableHeadline, String.format("form:group:%s", I18NSearchPageModelParts.SearchTableHeadline.wicketId()));
		paths.put(I18NSearchPageModelParts.NameHeader, String.format("form:group:%s",I18NSearchPageModelParts.NameHeader.wicketId()));
		paths.put(I18NSearchPageModelParts.CategoryHeader, String.format("form:group:%s", I18NSearchPageModelParts.CategoryHeader.wicketId()));
		paths.put(I18NSearchPageModelParts.DateHeader, String.format("form:group:%s",I18NSearchPageModelParts.DateHeader.wicketId()));
		paths.put(I18NSearchPageModelParts.ArchiveIdHeader, String.format("form:group:%s", I18NSearchPageModelParts.ArchiveIdHeader.wicketId()));
		
	
		final OneWayMapping<Locale,Enum<?>> oneWayMapping = Mockito.mock(OneWayMapping.class);
		Mockito.when(editPageModelWeb.getI18NMessages()).thenReturn(oneWayMapping);
		Mockito.when(editPageModelWeb.getI18NLabels()).thenReturn(oneWayMapping);
		
		final TwoWayMapping<Archive,Enum<?>> mock2 = Mockito.mock(TwoWayMapping.class);
		
		Mockito.when(editPageModelWeb.getArchiveModelWeb()).thenReturn(mock2);
		
		Mockito.when(editPageModelWeb.getI18NAttachementLabels()).thenReturn(oneWayMapping);
		Mockito.when(editPageModelWeb.getAttachements()).thenReturn(Mockito.mock(IModel.class));
		
		final IModel<Serializable> iModel = Mockito.mock(IModel.class);
		Mockito.when(mock2.part(Mockito.any())).thenReturn(iModel);
		Mockito.when(oneWayMapping.part(Mockito.any())).thenReturn(iModel);

	}

	@Test
	public final void labels() {

		Arrays.stream(I18NSearchPageModelParts.values()).forEach(part -> { 
			
		
			Assert.assertEquals(part.key(), tester.getComponentFromLastRenderedPage(paths.get(part)).getDefaultModel().getObject());
			
		
		}
		
				);

	}

	@Test
	public final void search() {

		final FormTester formTester = tester.newFormTester("searchForm");
		formTester.setValue("name", "kylie");
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
		final TwoWayMapping<Archive,Enum<?>> currentRow = Mockito.mock(TwoWayMapping.class);
		Mockito.when(searchPageController.newWebModel(row)).thenReturn(currentRow);
		Arrays.stream(ArchiveModelParts.values()).forEach(part -> Mockito.when(currentRow.part(part)).thenReturn(new Model<>(part.name())));

		formTester.submit("searchButton");

		Mockito.verify(actionListener).process(SearchPageController.SEARCH_ACTION);
		Assert.assertTrue(showButton.isEnabled());
		Assert.assertTrue(changeButton.isEnabled());

		Arrays.stream(new ArchiveModelParts[] { ArchiveModelParts.Id, ArchiveModelParts.ArchiveId, ArchiveModelParts.Name, ArchiveModelParts.Category, ArchiveModelParts.DocumentDate }).forEach(part -> Assert.assertEquals(part.name(), tester.getComponentFromLastRenderedPage(String.format("form:group:documents:0:%s", StringUtils.uncapitalize(part.name()))).getDefaultModel().getObject()));

	}


	
	@Test
	public final void ajaxListener() {

		final Button showButton = (Button) tester.getComponentFromLastRenderedPage(paths.get(I18NSearchPageModelParts.ShowButton));
		final Button changeButton = (Button) tester.getComponentFromLastRenderedPage(paths.get(I18NSearchPageModelParts.ChangeButton));
		Assert.assertFalse(showButton.isEnabled());
		Assert.assertFalse(changeButton.isEnabled());
		@SuppressWarnings("unchecked")
		final RadioGroup<String> radio = (RadioGroup<String>) tester.getComponentFromLastRenderedPage("form:group");
		final Behavior behavior = radio.getBehaviors().iterator().next();

		Mockito.when(searchPageModelWeb.isSelected()).thenReturn(true);
		tester.executeBehavior((AbstractAjaxBehavior) behavior);

		Assert.assertTrue(showButton.isEnabled());
		Assert.assertTrue(changeButton.isEnabled());
		tester.assertComponentOnAjaxResponse(changeButton);
		tester.assertComponentOnAjaxResponse(showButton);
		
	}
	
	@Test
	public final void newButton() {
	
		
		final FormTester formTester = tester.newFormTester("form");
		
		formTester.submit("group:" + I18NSearchPageModelParts.NewButton.wicketId() );
	
		Assert.assertEquals(EditPage.class, tester.getLastRenderedPage().getClass());
		
	}
	
	@Test
	public final void changeButton() {
		final Button changeButton = (Button) tester.getComponentFromLastRenderedPage(paths.get(I18NSearchPageModelParts.ChangeButton));
		Assert.assertFalse(changeButton.isEnabled());
		changeButton.setEnabled(true);
		final FormTester formTester = tester.newFormTester("form");
		
		formTester.submit("group:" + I18NSearchPageModelParts.ChangeButton.wicketId() );
	
		Assert.assertEquals(EditPage.class, tester.getLastRenderedPage().getClass());
	}

}
