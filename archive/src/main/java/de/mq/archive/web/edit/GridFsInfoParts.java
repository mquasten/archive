package de.mq.archive.web.edit;

import org.springframework.util.StringUtils;

import de.mq.archive.web.search.WicketIdAware;

public enum GridFsInfoParts implements WicketIdAware {
	ContentLength,

	Filename,

	LastModified,
	
	Id,

	ContentType;
	
	public final String wicketId() {
		
		return StringUtils.uncapitalize(name());
	}

}
