package de.mq.archive.web;


import java.util.Map;
import java.util.stream.Collectors;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public interface ActionListenerOperations  {
	
	
	default  void addActionListener(final String  key, final ActionListener actionListener) {
		final Map<String,ActionListener> actionListeners= ReflectionUtil.getFieldValue(this, ActionListeners.class);
		actionListeners.put(key, actionListener);
	}
	
	default void addActionListener(final ActionListener actionListener) {
		addActionListener(getId(), actionListener);
	}
	
	default void removeActionListener(final ActionListener actionListener) {
		final Map<String,ActionListener> actionListeners= ReflectionUtil.getFieldValue(this, ActionListeners.class);
		actionListeners.entrySet().stream().filter(entry  -> entry.getValue().equals(actionListener)).map(entry -> entry.getKey()).collect(Collectors.toSet()).forEach(key -> actionListeners.remove(key));
	}
	
	
	default void removeActionListener(final String key) {
		final Map<String,ActionListener> actionListeners= ReflectionUtil.getFieldValue(this, ActionListeners.class);
		actionListeners.remove(key);
	}
	
	
	String getId();
	


}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface ActionListeners {
}



