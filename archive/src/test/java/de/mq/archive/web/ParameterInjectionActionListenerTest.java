package de.mq.archive.web;

import java.lang.reflect.Method;
import java.util.Map;

import javax.inject.Named;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.test.util.ReflectionTestUtils;

public class ParameterInjectionActionListenerTest {

	private static final String INVALID_METHOD_ID = "onlyYouAndYourHand...";

	private static final String METHODS_FIELD = "methods";

	private static final String METHOD_01_ANNOTATED = "calculateHotScore";

	private static final String METHOD_NAME_02 = "requestForDate";

	private static final String METHOD_NAME_01 = "hotScore";

	private final BeanFactory beanFactory = Mockito.mock(BeanFactory.class);

	private final TestController testController = Mockito.mock(TestController.class);

	private ActionListener actionListener;
	
	private final Model model = Mockito.mock(Model.class);

	@Before
	public final void setup() {
		Mockito.when(beanFactory.getBean(TestController.class)).thenReturn(testController);
		Mockito.when(beanFactory.getBean(Model.class)).thenReturn(model);
		actionListener = new SimpleParameterInjectionActionListenerImpl(beanFactory, TestController.class);
	}

	@Test
	public final void create() {
		@SuppressWarnings("unchecked")
		final Map<String, Method> methods = (Map<String, Method>) (ReflectionTestUtils.getField(actionListener, METHODS_FIELD));
		Assert.assertEquals(2, methods.size());

		methods.keySet().forEach(key -> Assert.assertTrue(key.equals(METHOD_01_ANNOTATED) || key.equals(METHOD_NAME_02)));

		Assert.assertEquals(METHOD_NAME_01, methods.get(METHOD_01_ANNOTATED).getName());
		Assert.assertEquals(METHOD_NAME_02, methods.get(METHOD_NAME_02).getName());
	}
	
	@Test
	public final void injectIntoHotScore() {
		actionListener.process(METHOD_01_ANNOTATED);
		Mockito.verify(testController, Mockito.times(1)).hotScore(model);
	}
	
	@Test
	public final void injectIntorequestForDate() {
		actionListener.process(METHOD_NAME_02);
		Mockito.verify(testController, Mockito.times(1)).requestForDate(model);
	}
	
	@Test
	public final void processNothing() {
		actionListener.process(INVALID_METHOD_ID);
		Mockito.verifyZeroInteractions(testController);
	}


}


abstract class TestController {

	@Named("calculateHotScore")
	abstract void hotScore(final Model model);

	@Named()
	abstract void requestForDate(final Model model );
}

interface Model {
	
}
