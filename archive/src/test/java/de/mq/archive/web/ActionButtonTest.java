package de.mq.archive.web;

import java.util.Map;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class ActionButtonTest {

	private static final String LISTENERS_FIELD = "listeners";
	private static final String KEY = "calculateHotScore";
	private static final String BUTTON_VALUE = "I18NLabel";
	private static final String WICKET_ID = "wicketId";
	private ActionButton<String> actionButton;
	private WicketTester tester;
	@SuppressWarnings("unchecked")
	private ActionListener<String> actionListener = Mockito.mock(ActionListener.class);
	@SuppressWarnings("unchecked")
	final IModel<String> model = Mockito.mock(IModel.class);

	@Before
	public final void setup() {
		if (tester == null) {
			tester = new WicketTester();
		}
		actionButton = new ActionButton<String>(WICKET_ID);
		Mockito.when(model.getObject()).thenReturn(BUTTON_VALUE);
	}

	@Test
	public final void createWithWicketId() {

		Assert.assertEquals(WICKET_ID, actionButton.getId());
	}

	@Test
	public final void createWithWicketIdAndModel() {
		actionButton = new ActionButton<String>(WICKET_ID, model);
		Assert.assertEquals(WICKET_ID, actionButton.getId());
		Assert.assertEquals(BUTTON_VALUE, actionButton.getValue());

	}

	@Test
	public final void addActionListener() {
		actionButton.addActionListener(KEY, actionListener);
		final Map<String, ActionListener<String>> listeners = listeners();
		Assert.assertEquals(1, listeners.size());
		Assert.assertEquals(KEY, listeners.keySet().iterator().next());
		Assert.assertEquals(actionListener, listeners.values().iterator().next());
	}

	@SuppressWarnings("unchecked")
	private Map<String, ActionListener<String>> listeners() {
		return (Map<String, ActionListener<String>>) ReflectionTestUtils.getField(actionButton, LISTENERS_FIELD);
	}

	@Test
	public final void addActionListenerDefault() {
		actionButton.addActionListener(actionListener);

		final Map<String, ActionListener<String>> listeners = listeners();
		Assert.assertEquals(1, listeners.size());
		Assert.assertEquals(WICKET_ID, listeners.keySet().iterator().next());
		Assert.assertEquals(actionListener, listeners.values().iterator().next());
	}

	@Test
	public final void removeActionListener() {
		final Map<String, ActionListener<String>> listeners = listeners();
		listeners.put(KEY, actionListener);
		Assert.assertFalse(listeners.isEmpty());
		actionButton.removeActionListener(actionListener);
		Assert.assertTrue(listeners.isEmpty());
	}

	@Test
	public final void removeActionListenerbyKey() {
		final Map<String, ActionListener<String>> listeners = listeners();
		listeners.put(KEY, actionListener);
		Assert.assertFalse(listeners.isEmpty());
		actionButton.removeActionListener(KEY);
		Assert.assertTrue(listeners.isEmpty());
	}

	@Test
	public final void getActionListeners() {
		final Map<String, ActionListener<String>> listeners = listeners();
		listeners.put(KEY, actionListener);
		Assert.assertEquals(listeners, actionButton.getActionListeners());
	}

	@Test
	public final void onSubmit() {
		final Map<String, ActionListener<String>> listeners = listeners();
		listeners.put(KEY, actionListener);

		actionButton.onSubmit();

		Mockito.verify(actionListener, Mockito.times(1)).process(KEY);
	}

}
