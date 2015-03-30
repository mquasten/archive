package de.mq.archive.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.wicket.markup.html.form.ImageButton;
import org.apache.wicket.model.IModel;

public class ActionImageButton<T> extends ImageButton   {

	private static final long serialVersionUID = 1L;
	
	private final Map<T,ActionListener<T>> listeners = new HashMap<>();
	
	public ActionImageButton(final String id, final String resource) {
		super(id, resource);
	}
	
	public ActionImageButton(final String id, final String resource, final T action, ActionListener<T> listener) {
		super(id,  resource);
	
		addActionListener(action,listener );
	}
	
	public ActionImageButton(final String id,  final IModel<String> model) {
		super(id, model);
	}

	

	public final void addActionListener(final T key, final ActionListener<T> actionListener) {
		listeners.put(key, actionListener);
	}
	
	
	@SuppressWarnings("unchecked")
	public final void addActionListener(final ActionListener<T> actionListener) {
		listeners.put( (T) getId(), actionListener);
	}
	
	
	
	public final void removeActionListener(final ActionListener<T> actionListener) {
		listeners.entrySet().stream().filter(entry  -> entry.getValue().equals(actionListener)).map(entry -> entry.getKey()).collect(Collectors.toSet()).forEach(key -> listeners.remove(key));
	}
	
	
	public final void removeActionListener(T key) {
		listeners.remove(key);
	}
	
	
	
	public final Map<T, ActionListener<T>> getActionListeners() {
		return Collections.unmodifiableMap(listeners);
	}


	/*
	 * (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.Button#onSubmit()
	 */
	@Override
	public void onSubmit() {
		listeners.entrySet().forEach(e -> listeners.get(e.getKey()).process((T) e.getKey()));
		super.onSubmit();
		
	}
	
	
	
	
	

}
