package de.mq.archive.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;

public class ActionButton extends Button  {

	private static final long serialVersionUID = 1L;
	
	private transient final List<ActionListener> listeners = new ArrayList<>();
	
	public ActionButton(final String id) {
		super(id);
	}
	
	public ActionButton(final String id,  final IModel<String> model) {
		super(id, model);
	}

	
	public final void addActionListener(final ActionListener actionEvent) {
		listeners.add(actionEvent);
	}
	
	
	public final void removeActionListener(final ActionListener actionEvent) {
		listeners.add(actionEvent);
	}
	
	
	public final Collection<ActionListener> getActionListeners() {
		return Collections.unmodifiableList(listeners);
	}


	@Override
	public void onSubmit() {
		listeners.forEach(listener -> listener.process(getId()));
		super.onSubmit();
	}
	
	

}
