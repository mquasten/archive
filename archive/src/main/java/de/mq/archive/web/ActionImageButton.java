package de.mq.archive.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.form.ImageButton;
import org.apache.wicket.model.IModel;

public class ActionImageButton extends ImageButton implements ActionListenerOperations   {

	private static final long serialVersionUID = 1L;
	
	private final Map<String, ActionListener> listeners = new HashMap<>();
	
	public ActionImageButton(final String id, final String resource) {
		super(id, resource);
	}
	
	public ActionImageButton(final String id, final String resource, final String action, ActionListener  listener) {
		super(id,  resource);
	
		addActionListener(action,listener );
	}
	
	public ActionImageButton(final String id,  final IModel<String> model) {
		super(id, model);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.Button#onSubmit()
	 */
	@Override
	public void onSubmit() {
		listeners.entrySet().forEach(e -> getActionListeners().get(e.getKey()).process( e.getKey()));
		super.onSubmit();
		
	}

	@Override
	public Map<String, ActionListener> getActionListeners() {
		return listeners;
	}

	
	
	
	
	
	

}
