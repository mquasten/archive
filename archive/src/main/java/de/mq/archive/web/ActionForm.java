package de.mq.archive.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.wicket.markup.html.form.Form;


public class ActionForm<T>  extends Form<T> implements ActionListenerOperations<T>  {
	
	private static final long serialVersionUID = 1L;
	private final Map<T,ActionListener<T>> listeners = new HashMap<>();
	
	
	
	public ActionForm(final String id) {
		super(id);
	   
	}
	


	



	@Override
	public void addActionListener(final T key, final ActionListener<T> actionListener) {
		listeners.put( key, actionListener);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addActionListener(final ActionListener<T> actionListener) {
		listeners.put( (T) getId(), actionListener);
		
	}

	@Override
	public void removeActionListener(final  ActionListener<T> actionListener) {
		listeners.entrySet().stream().filter(entry  -> entry.getValue().equals(actionListener)).map(entry -> entry.getKey()).collect(Collectors.toSet()).forEach(key -> listeners.remove(key));
		
	}

	@Override
	public void removeActionListener(final T key) {
		listeners.remove(key);
		
	}

	@Override
	public Map<T, ActionListener<T>> getActionListeners() {
		return Collections.unmodifiableMap(listeners);
	}
	
	@Override
	public void onSubmit() {
		System.out.println("!!!submit");
		System.out.println(listeners);
		listeners.entrySet().forEach(e -> listeners.get(e.getKey()).process(e.getKey()));
		super.onSubmit();
		
	}

	

}
