package de.mq.archive.domain.support;

import java.util.Date;

import de.mq.archive.domain.GridFsInfo;

public class Constants {
	
	public static final GridFsInfo<String> create (final String id, final String filename, final Number contentLength, final Date lastModified, final String contentType ) {
		return new GridFsInfoImpl(id, filename, contentLength, lastModified, contentType);
	}

}
