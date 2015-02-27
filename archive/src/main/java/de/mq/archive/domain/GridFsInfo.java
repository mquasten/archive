package de.mq.archive.domain;

import java.util.Date;


public interface GridFsInfo<T> {
	
	Number contentLength();

	String filename();

	Date lastModified();
	
	T id();

	String contentType();

}
