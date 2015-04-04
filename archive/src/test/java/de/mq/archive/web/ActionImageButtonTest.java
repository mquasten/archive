package de.mq.archive.web;



import java.util.Map;

import org.apache.wicket.markup.html.form.ImageButton;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class ActionImageButtonTest {
	
	private static final String ACTION = "action";
	private static final String RESOURCE = "/kylie.png";
	private static final String WICKET_ID = "downloadButton";
	private WicketTester tester;
	private ActionListener actionListener = Mockito.mock(ActionListener.class);
	

	@Before
	public final void setup() {
		if (tester == null) {
			tester = new WicketTester();
		}
	}
	
	@Test
	public final void create() {
		final ImageButton button = new ActionImageButton(WICKET_ID , RESOURCE );
		Assert.assertEquals(WICKET_ID, button.getId());
		Assert.assertEquals(RESOURCE, button.getModel().getObject());
	}
	
	@Test
	public final void createWithActionListener() {
		
		final ImageButton button = new ActionImageButton(WICKET_ID , RESOURCE, ACTION, actionListener );
		Assert.assertEquals(WICKET_ID, button.getId());
		Assert.assertEquals(RESOURCE, button.getModel().getObject());
		final Map<String,ActionListener> results = listeners(button);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(ACTION, results.keySet().stream().findFirst().get());
		Assert.assertEquals(actionListener, results.values().stream().findFirst().get());
	}

	@SuppressWarnings("unchecked")
	private Map<String, ActionListener> listeners(final Object button) {
		return (Map<String, ActionListener>) ReflectionTestUtils.getField(button, "listeners");
	}
	
	@Test
	public final void createWithModel() {
		@SuppressWarnings("unchecked")
		final IModel<String> model = Mockito.mock(IModel.class);
		final ImageButton button = new ActionImageButton(WICKET_ID , model );
		Assert.assertEquals(WICKET_ID, button.getId());
		Assert.assertEquals(model, button.getModel());
	}
	
	
	
	@Test
	public final void  submit(){
		final ImageButton button = new ActionImageButton(WICKET_ID , RESOURCE );
		final Map<String,ActionListener> results = listeners(button);
		results.put(ACTION, actionListener);
		button.onSubmit();
		
		Mockito.verify(actionListener).process(ACTION);
		
		
	}

}
