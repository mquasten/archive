package de.mq.archive.web;


import java.util.Map;
import java.util.stream.Collectors;

public interface ActionListenerOperations {
	
	
	default  void addActionListener(final String  key, final ActionListener actionListener) {
		getActionListeners().put(key, actionListener);
	}
	
	default void addActionListener(final ActionListener actionListener) {
		getActionListeners().put(   getId() , actionListener);
	}
	
	default void removeActionListener(final ActionListener actionListener) {
		getActionListeners().entrySet().stream().filter(entry  -> entry.getValue().equals(actionListener)).map(entry -> entry.getKey()).collect(Collectors.toSet()).forEach(key -> getActionListeners().remove(key));
	}
	
	
	default void removeActionListener(final String key) {
		getActionListeners().remove(key);
	}
	
	
	
	Map<String, ActionListener> getActionListeners();
	
	String getId();
	
	
}
