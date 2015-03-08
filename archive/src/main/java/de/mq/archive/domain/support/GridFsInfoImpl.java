package de.mq.archive.domain.support;

import java.util.Date;

import de.mq.archive.domain.GridFsInfo;

class GridFsInfoImpl implements GridFsInfo<String>{

	
	private String id;
	private String filename;
	private Number contentLength;
	private Date lastModified;
	private String contentType;
	
	
	GridFsInfoImpl(String id, String filename, Number contentLength, Date lastModified, String contentType) {
		
		this.id = id;
		this.filename = filename;
		this.contentLength = contentLength;
		this.lastModified = lastModified;
		this.contentType = contentType;
	}

	@Override
	public Number contentLength() {
		
		return contentLength;
	}

	@Override
	public String filename() {
		// TODO Auto-generated method stub
		return filename;
	}

	@Override
	public Date lastModified() {
		// TODO Auto-generated method stub
		return lastModified;
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public String contentType() {
		return contentType;
	}

}
