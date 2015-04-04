package de.mq.archive.web;

import java.util.HashMap;
import java.util.Map;



import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ReflectionUtilTest {
	
	private ActionListener listener = Mockito.mock(ActionListener.class);
	private static final String EVENT_ID = "eventId";
	@Test
	public final void getFieldValue() {
		
		final Map<String,ActionListener> results = ReflectionUtil.getFieldValue(new ActionButtonMock(),ActionListeners.class);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(listener, results.get(EVENT_ID));
	}
	
	class ActionButtonMock {
		
		@ActionListeners
		private final Map<String,ActionListener> listeners = new HashMap<>();
		
		ActionButtonMock() {
		   listeners.put(EVENT_ID, listener);	
		}
		
	}

}
