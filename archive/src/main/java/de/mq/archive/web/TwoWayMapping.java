package de.mq.archive.web;


public interface TwoWayMapping<Domain, Part>  extends OneWayMapping<Domain, Part> {        
	
	Domain toDomain(); 
	
}
