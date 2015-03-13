package de.mq.archive.web.edit;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.springframework.util.StringUtils;

import de.mq.archive.web.ActionButton;
import de.mq.archive.web.search.WicketIdAware;

public enum I18NAttachementsModelParts implements WicketIdAware {
	
	FilenameHeader("archive_edit_attachement_filename", Label.class),
	ContentTypeHeader("archive_edit_attachement_contentType",Label.class),
	ContentLengthHeader("archive_edit_attachement_contentLength", Label.class),
	DeleteButton("archive_edit_attachement_delete" , ActionButton.class),
	ShowButton("archive_edit_attachement_show" , ActionButton.class),
	UploadButton("archive_edit_attachement_upload" , ActionButton.class);

	private final String key;
	private final Class<? extends Component> targetClass;
	I18NAttachementsModelParts(final String key, final Class<? extends Component> targetClass) {
		this.key=key;
		this.targetClass=targetClass;
	}
	public String wicketId() {
		return  StringUtils.uncapitalize(name());
	}
	
	public final String key() {
		return key;
	}
	
	public Class<? extends Component> targetClass() {
		return targetClass;
	}

}
