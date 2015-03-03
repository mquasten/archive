package de.mq.archive.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;

public class ActionButton<T> extends Button implements ActionListenerOperations<T>  {

	private static final long serialVersionUID = 1L;
	
	private final Map<String,ActionListener<T>> listeners = new HashMap<>();
	
	public ActionButton(final String id) {
		super(id);
	}
	
	public ActionButton(final String id,  final IModel<String> model) {
		super(id, model);
	}

	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.ActionListenerOperations#addActionListener(T, de.mq.archive.web.ActionListener)
	 */
	@Override
	public final void addActionListener(final String key, final ActionListener<T> actionListener) {
		listeners.put(key, actionListener);
	}
	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.ActionListenerOperations#addActionListener(de.mq.archive.web.ActionListener)
	 */
	@Override
	public final void addActionListener(final ActionListener<T> actionListener) {
		listeners.put( getId(), actionListener);
	}
	
	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.ActionListenerOperations#removeActionListener(de.mq.archive.web.ActionListener)
	 */
	@Override
	public final void removeActionListener(final ActionListener<T> actionListener) {
		listeners.entrySet().stream().filter(entry  -> entry.getValue().equals(actionListener)).map(entry -> entry.getKey()).collect(Collectors.toSet()).forEach(key -> listeners.remove(key));
	}
	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.ActionListenerOperations#removeActionListener(T)
	 */
	@Override
	public final void removeActionListener(String key) {
		listeners.remove(key);
	}
	
	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.ActionListenerOperations#getActionListeners()
	 */
	@Override
	public final Map<String, ActionListener<T>> getActionListeners() {
		return Collections.unmodifiableMap(listeners);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void onSubmit() {
		listeners.entrySet().forEach(e -> listeners.get(e.getKey()).process((T) e.getKey()));
		super.onSubmit();
		
	}
	
	
	
	
	

}
