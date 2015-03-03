package de.mq.archive.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.wicket.markup.html.form.Form;


public class ActionForm<T,M>   extends Form<T> implements ActionListenerOperations<M>  {
	
	private static final long serialVersionUID = 1L;
	private final Map<String,ActionListener<M>> listeners = new HashMap<>();
	
	private final M model;
	
	public ActionForm(final String id, final M model ) {
		super("id");
	   this.model=model; 
	}
	


	



	@Override
	public void addActionListener(final String key, final ActionListener<M> actionListener) {
		listeners.put(key, actionListener);
		
	}

	@Override
	public void addActionListener(final ActionListener<M> actionListener) {
		listeners.put( getId(), actionListener);
		
	}

	@Override
	public void removeActionListener(final  ActionListener<M> actionListener) {
		listeners.entrySet().stream().filter(entry  -> entry.getValue().equals(actionListener)).map(entry -> entry.getKey()).collect(Collectors.toSet()).forEach(key -> listeners.remove(key));
		
	}

	@Override
	public void removeActionListener(final String key) {
		listeners.remove(key);
		
	}

	@Override
	public Map<String, ActionListener<M>> getActionListeners() {
		return Collections.unmodifiableMap(listeners);
	}
	
	@Override
	public void onSubmit() {
		listeners.entrySet().forEach(e -> listeners.get(e.getKey()).process(model));
		super.onSubmit();
		
	}

	

}
