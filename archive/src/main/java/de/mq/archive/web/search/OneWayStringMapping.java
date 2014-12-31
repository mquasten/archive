package de.mq.archive.web.search;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

public interface OneWayStringMapping<Domain,Part> extends Serializable {

	void intoWeb(final Domain source); 
	
	IModel<String> part(final Part part);

}
