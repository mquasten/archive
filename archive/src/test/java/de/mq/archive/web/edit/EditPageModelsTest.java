package de.mq.archive.web.edit;

import java.util.Arrays;

import javax.servlet.ServletContext;

import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.WebApplicationContext;

import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.BasicEnumModelImpl;
import de.mq.archive.web.SimpleParameterInjectionActionListenerImpl;
import de.mq.archive.web.WicketApplication;
import de.mq.archive.web.search.ArchiveModelParts;

public class EditPageModelsTest {

	private static final String CONTROLLER_FIELD = "controller";
	private static final String MESSAGE_SOURCE_FIELD = "messageSource";
	private static final String BEAN_FACTORY_FIELD = "beanFactory";
	private final EditPageModels editPageModels = new EditPageModels();
	private final MessageSource messageSource = Mockito.mock(MessageSource.class);
	private final BeanFactory beanFactory = Mockito.mock(BeanFactory.class);
	private final EditPageController editPageController = Mockito.mock(EditPageController.class);

	@Before
	public final void setup() {
		ReflectionTestUtils.setField(editPageModels, MESSAGE_SOURCE_FIELD, messageSource);
		ReflectionTestUtils.setField(editPageModels, BEAN_FACTORY_FIELD, beanFactory);
		Mockito.when(beanFactory.getBean(EditPageController.class)).thenReturn(editPageController);
	}

	@Test
	public final void editPageModel() {

		final EditPageModelImpl result = editPageModels.editPageModel();
		Assert.assertEquals(ArchiveImpl.class, result.getArchive().getClass());

		Assert.assertEquals(BasicEnumModelImpl.class, result.getArchiveModelWeb().getClass());

		Arrays.stream(ArchiveModelParts.values()).forEach(part -> Assert.assertNull(result.getArchiveModelWeb().part(part).getObject()));

		Arrays.stream(I18NEditPageModelParts.values()).forEach(part -> Assert.assertNull(result.getI18NLabels().part(part).getObject()));

		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(part -> Assert.assertNull(result.getI18NMessages().part(part).getObject()));

		Assert.assertEquals(messageSource, ReflectionTestUtils.getField(result.getI18NLabels(), MESSAGE_SOURCE_FIELD));
		Assert.assertEquals(messageSource, ReflectionTestUtils.getField(result.getI18NMessages(), MESSAGE_SOURCE_FIELD));

	}

	@Test
	public final void editActionListener() {

		final ActionListener<?> listener = editPageModels.editActionListener();
		Assert.assertTrue(listener instanceof SimpleParameterInjectionActionListenerImpl);

		Assert.assertEquals(beanFactory, ReflectionTestUtils.getField(listener, BEAN_FACTORY_FIELD));
		Assert.assertEquals(editPageController, ReflectionTestUtils.getField(listener, CONTROLLER_FIELD));
	}

	@Test
	public final void initEditActionListener() {
		final ActionListener<?> listener = editPageModels.initEditActionListener();
		Assert.assertTrue(listener instanceof SimpleParameterInjectionActionListenerImpl);

		Assert.assertEquals(beanFactory, ReflectionTestUtils.getField(listener, BEAN_FACTORY_FIELD));
		Assert.assertEquals(editPageController, ReflectionTestUtils.getField(listener, CONTROLLER_FIELD));
	}

	@Test
	public final void newEditActionListener() {
		final ActionListener<?> listener = editPageModels.newEditActionListener();
		Assert.assertTrue(listener instanceof SimpleParameterInjectionActionListenerImpl);

		Assert.assertEquals(beanFactory, ReflectionTestUtils.getField(listener, BEAN_FACTORY_FIELD));
		Assert.assertEquals(editPageController, ReflectionTestUtils.getField(listener, CONTROLLER_FIELD));
	}

	@Test
	public final void fileUploadField() {
		final ServletContext ctx = Mockito.mock(ServletContext.class);

		final WebApplicationContext webApplicationContext = Mockito.mock(WebApplicationContext.class);
		final WicketApplication wicketApplication = new WicketApplication();
		Mockito.when(ctx.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)).thenReturn(webApplicationContext);
		new WicketTester(wicketApplication, ctx);
		Assert.assertTrue(editPageModels.fileUploadField() instanceof FileUploadField);

	}

}
