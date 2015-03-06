package de.mq.archive.web;

import java.util.Map;

public interface ActionListenerOperations<T> {

	public abstract void addActionListener(T key, ActionListener<T> actionListener);

	public abstract void addActionListener(ActionListener<T> actionListener);

	public abstract void removeActionListener(ActionListener<T> actionListener);

	public abstract void removeActionListener(T key);

	public abstract Map<T, ActionListener<T>> getActionListeners();

}