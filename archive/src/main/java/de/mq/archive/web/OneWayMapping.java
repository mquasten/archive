package de.mq.archive.web;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

public interface OneWayMapping<Domain, Part> extends Serializable {

	void intoWeb(final Domain source); 
	
	<T extends Serializable> IModel<T>  part(final Part part);

	
}
