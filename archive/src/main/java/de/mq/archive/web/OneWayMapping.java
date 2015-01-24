package de.mq.archive.web;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

public interface OneWayMapping<Domain, Part> extends Serializable {

	void intoWeb(final Domain source); 
	
	<T> IModel<T> part(final Part part, final Class<T> clazz);

	
}
