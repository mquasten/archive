package de.mq.archive.web.edit;

import org.springframework.util.StringUtils;

import de.mq.archive.web.search.WicketIdAware;

public enum I18NEditPageModelParts implements WicketIdAware {
	
	ApplicationHeadline("archive_headline"),
	PageHeadline("archive_edit_headline"),
	
	NameLabel("archive_edit_name"),
	
	
	CategoryLabel("archive_edit_category"),
	
	
	ArchiveIdLabel("archive_edit_archive_id"),
	

	DocumentDateLabel("archive_edit_document_date"),
	
	
	TextLabel("archive_edit_text"),
	
	
	CancelButton("archive_edit_cancel_button"),
	SaveButton("archive_edit_save_button");
	
	I18NEditPageModelParts(final String key) {
		this.key=key;
	}
	
	private final String key;
	@Override
	public String wicketId() {
		return  StringUtils.uncapitalize(name());
	}
	
	public final String key() {
		return key;
	}

}
