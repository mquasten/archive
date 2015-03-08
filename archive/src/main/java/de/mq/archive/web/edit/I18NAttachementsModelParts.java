package de.mq.archive.web.edit;

import org.springframework.util.StringUtils;

import de.mq.archive.web.search.WicketIdAware;

public enum I18NAttachementsModelParts implements WicketIdAware {
	
	FilenameHeader("archive_edit_attachement_filename"),
	ContentTypeHeader("archive_edit_attachement_contentType"),
	ContentLengthHeader("archive_edit_attachement_contentLength"); 

	private final String key;
	I18NAttachementsModelParts(final String key) {
		this.key=key;
	}
	public String wicketId() {
		return  StringUtils.uncapitalize(name());
	}
	
	public final String key() {
		return key;
	}

}
