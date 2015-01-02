package de.mq.archive.web;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

public interface TwoWayMapping<Domain, Part>  extends Serializable{
	
	void intoWeb(final Domain source);
	
	Domain toDomain(); 
	
	<T> IModel<T> part(Part part, Class<T> clazz);


}
