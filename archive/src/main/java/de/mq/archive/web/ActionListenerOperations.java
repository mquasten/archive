package de.mq.archive.web;

import java.util.Map;

public interface ActionListenerOperations<T> {

	public abstract void addActionListener(String key, ActionListener<T> actionListener);

	public abstract void addActionListener(ActionListener<T> actionListener);

	public abstract void removeActionListener(ActionListener<T> actionListener);

	public abstract void removeActionListener(String key);

	public abstract Map<String, ActionListener<T>> getActionListeners();

}