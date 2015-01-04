package de.mq.archive.domain.support;


public interface ModifyablePaging  extends Paging {
	 boolean  inc();


	boolean  dec(); 
	
	boolean  first(); 
	
	boolean last(); 
}
