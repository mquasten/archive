package de.mq.archive.web.edit;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.wicket.Component;
import org.apache.wicket.IGenericComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.WebApplicationContext;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.ActionButton;
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.ComponentFactory;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TestConstants;
import de.mq.archive.web.TwoWayMapping;
import de.mq.archive.web.WicketApplication;
import de.mq.archive.web.search.ArchiveModelParts;
import de.mq.archive.web.search.SearchPage;
import de.mq.archive.web.search.SearchPageModelWeb;

public class EditPageTest {
	private static final String ARCHIVE_NAME = "loveletter for kylie";
	private final ServletContext ctx = Mockito.mock(ServletContext.class);
	private final WebApplicationContext webApplicationContext = Mockito.mock(WebApplicationContext.class);

	@SuppressWarnings("unchecked")
	private final ActionListener<String> actionListener = Mockito.mock(ActionListener.class);

	final SearchPageModelWeb searchPageModelWeb = Mockito.mock(SearchPageModelWeb.class);

	private WicketApplication wicketApplication = new WicketApplication();

	private Map<Class<?>, Object> beans = new HashMap<>();

	private EditPageModelWeb editPageModelWeb = Mockito.mock(EditPageModelWeb.class);
	private WicketTester tester;
	private EditPage page;

	@Before
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final void setup() {

		Mockito.doAnswer(a -> new String[] { ((Class<?>) a.getArguments()[0]).getName() }).when(webApplicationContext).getBeanNamesForType(Mockito.any());

		Mockito.when(ctx.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)).thenReturn(webApplicationContext);

		final ArgumentCaptor<Class> clazzCaptor = ArgumentCaptor.forClass(Class.class);

		beans.put(ActionListener.class, actionListener);
		beans.put(ComponentFactory.class, TestConstants.COMPONENT_FACTORY);
		beans.put(EditPageModelWeb.class, editPageModelWeb);
		beans.put(SearchPageModelWeb.class, searchPageModelWeb);
		

		Mockito.doAnswer(a -> beans.get((Class<?>) a.getArguments()[1])).when(webApplicationContext).getBean(Mockito.anyString(), clazzCaptor.capture());

		final OneWayMapping attachementLabels = Mockito.mock(OneWayMapping.class);
		final OneWayMapping searchModelLabels = attachementLabels;
		Mockito.when(searchPageModelWeb.getI18NLabels()).thenReturn(searchModelLabels);
		TwoWayMapping<Archive, Enum<?>> criteriaModel = Mockito.mock(TwoWayMapping.class);
		Mockito.when(searchPageModelWeb.getSearchCriteriaWeb()).thenReturn(criteriaModel);

		Mockito.when(searchPageModelWeb.getArchivesWeb()).thenReturn(new ListModel());
		final OneWayMapping<Locale, Enum<?>> messages = attachementLabels;
		final OneWayMapping<Locale, Enum<?>> oneWayMapping = attachementLabels;
		Mockito.when(editPageModelWeb.getI18NMessages()).thenReturn(messages);
		Mockito.when(editPageModelWeb.getI18NLabels()).thenReturn(oneWayMapping);
		
		Mockito.when(editPageModelWeb.getI18NAttachementLabels()).thenReturn(attachementLabels);
		
		final IModel attachementsListModel = Mockito.mock(IModel.class);
		Mockito.when(editPageModelWeb.getAttachements()).thenReturn(attachementsListModel);

		final TwoWayMapping<Archive, Enum<?>> mock2 = Mockito.mock(TwoWayMapping.class);

		Mockito.when(editPageModelWeb.getArchiveModelWeb()).thenReturn(mock2);

		final IModel<Serializable> iModel = attachementsListModel;
		Mockito.when(mock2.part(Mockito.any())).thenReturn(iModel);
		Mockito.when(oneWayMapping.part(Mockito.any())).thenReturn(iModel);
		Mockito.when(messages.part(Mockito.any())).thenReturn(iModel);

		Arrays.stream(I18NEditPageModelParts.values()).forEach(p -> Mockito.when(oneWayMapping.part(p)).thenReturn(new Model<>(p.key())));

		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(p -> Mockito.when(messages.part(p)).thenReturn(new Model<>(p.key())));

		tester = new WicketTester(wicketApplication, ctx);
		
		beans.put(IGenericComponent.class, new FileUploadField("fileUpload"));
		page = new EditPage(null);
		tester.startPage(page);

	}

	@Test
	public final void fields() {
		@SuppressWarnings("unchecked")
		final Map<ArchiveModelParts, Component> inputfields = (Map<ArchiveModelParts, Component>) ReflectionTestUtils.getField(page, "autoGeneratedFields");
		inputfields.keySet().forEach(p -> Assert.assertEquals(inputfields.get(p), tester.getComponentFromLastRenderedPage(String.format("%s:%s", EditPage.FORM_NAME, p.wicketId())).getClass()));
		Arrays.stream(I18NEditPageModelParts.values()).filter(p -> p.isButton()).forEach(p -> Assert.assertEquals(ActionButton.class, tester.getComponentFromLastRenderedPage(String.format("%s:%s", EditPage.FORM_NAME, p.wicketId())).getClass()));

		Arrays.stream(I18NEditPageModelParts.values()).filter(p -> p.isWithInForm()).forEach(p -> Assert.assertEquals(p.key(), tester.getComponentFromLastRenderedPage(String.format("%s:%s", EditPage.FORM_NAME, p.wicketId())).getDefaultModelObject()));
		Arrays.stream(I18NEditPageModelParts.values()).filter(p -> !p.isWithInForm()).forEach(p -> Assert.assertEquals(p.key(), tester.getComponentFromLastRenderedPage(p.wicketId()).getDefaultModelObject()));
		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(p -> Assert.assertEquals(p.key(), tester.getComponentFromLastRenderedPage(String.format("%s:%s", EditPage.FORM_NAME, p.wicketId()), false).getDefaultModelObject()));
		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(p -> Assert.assertFalse(tester.getComponentFromLastRenderedPage(String.format("%s:%s", EditPage.FORM_NAME, p.wicketId()), false).isVisible()));
		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(p -> Assert.assertEquals(FeedbackPanel.class, tester.getComponentFromLastRenderedPage(String.format("%s:%s", EditPage.FORM_NAME, p.wicketIdFeedback()), false).getClass()));

	}

	@Test
	public final void cancel() {

		final FormTester formTester = tester.newFormTester(EditPage.FORM_NAME);
		formTester.submit(I18NEditPageModelParts.CancelButton.wicketId());
		Assert.assertEquals(SearchPage.class, tester.getLastRenderedPage().getClass());
	}

	@Test
	public final void saveWithoutInputs() {
		final FormTester formTester = tester.newFormTester(EditPage.FORM_NAME);
		formTester.submit(I18NEditPageModelParts.SaveButton.wicketId());

		Arrays.stream(I18NEditPageMessagesParts.values()).filter(x -> x == I18NEditPageMessagesParts.Name).forEach(x -> Assert.assertTrue(((FeedbackPanel) tester.getComponentFromLastRenderedPage(EditPage.FORM_NAME + ":" + x.wicketIdFeedback(), false)).anyMessage()));
		Arrays.stream(I18NEditPageMessagesParts.values()).filter(x -> x != I18NEditPageMessagesParts.Name).forEach(x -> Assert.assertFalse(((FeedbackPanel) tester.getComponentFromLastRenderedPage(EditPage.FORM_NAME + ":" + x.wicketIdFeedback(), false)).anyMessage()));
		Arrays.stream(I18NEditPageMessagesParts.values()).filter(x -> x == I18NEditPageMessagesParts.Name).forEach(x -> Assert.assertTrue(((Label) tester.getComponentFromLastRenderedPage(EditPage.FORM_NAME + ":" + x.wicketId(), false)).isVisible()));
		Arrays.stream(I18NEditPageMessagesParts.values()).filter(x -> x != I18NEditPageMessagesParts.Name).forEach(x -> Assert.assertFalse(((Label) tester.getComponentFromLastRenderedPage(EditPage.FORM_NAME + ":" + x.wicketId(), false)).isVisible()));

		Assert.assertEquals(EditPage.class, tester.getLastRenderedPage().getClass());

	}

	@Test
	public final void save() {
		@SuppressWarnings("unchecked")
		final TwoWayMapping<Archive, Enum<?>> archiveModel = Mockito.mock(TwoWayMapping.class);
		@SuppressWarnings("unchecked")
		IModel<Serializable> emptyValueModel = Mockito.mock(IModel.class);

		@SuppressWarnings("unchecked")
		IModel<Serializable> nameModel = Mockito.mock(IModel.class);
		Mockito.when(archiveModel.part(ArchiveModelParts.Name)).thenReturn(nameModel);
		Mockito.when(nameModel.getObject()).thenReturn(ARCHIVE_NAME);
		Mockito.when(editPageModelWeb.getArchiveModelWeb()).thenReturn(archiveModel);

		Arrays.stream(ArchiveModelParts.values()).filter(part -> part != ArchiveModelParts.Name).forEach(part -> Mockito.when(archiveModel.part(part)).thenReturn(emptyValueModel));

		page = new EditPage(null);
		tester.startPage(page);

		final FormTester formTester = tester.newFormTester(EditPage.FORM_NAME);
		formTester.submit(I18NEditPageModelParts.SaveButton.wicketId());
		Assert.assertEquals(SearchPage.class, tester.getLastRenderedPage().getClass());

		Mockito.verify(actionListener).process(EditPageController.SAVE_ACTION);
	}

}
