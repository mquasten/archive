package de.mq.archive.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;

public class ActionButton<T> extends Button  implements ActionListenerOperations  {

	private static final long serialVersionUID = 1L;
	
	private final Map<String,ActionListener> listeners = new HashMap<>();
	
	public ActionButton(final String id) {
		super(id);
	}
	
	public ActionButton(final String id,  final IModel<String> model) {
		super(id, model);
	}

	
	public final Map<String, ActionListener> getActionListeners() {
		return listeners;
	}


	/*
	 * (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.Button#onSubmit()
	 */
	@Override
	public void onSubmit() {
		listeners.entrySet().forEach(e -> listeners.get(e.getKey()).process( e.getKey()));
		super.onSubmit();
		
	}
	
	
	
	
	

}
