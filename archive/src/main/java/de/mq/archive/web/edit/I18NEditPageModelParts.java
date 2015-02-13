package de.mq.archive.web.edit;

import org.springframework.util.StringUtils;

import de.mq.archive.web.search.WicketIdAware;

public enum I18NEditPageModelParts implements WicketIdAware {
	
	ApplicationHeadline("archive_headline", false, false),
	PageHeadline("archive_edit_headline", false, false),
	
	NameLabel("archive_edit_name", true, false),
	
	
	CategoryLabel("archive_edit_category", true, false),
	
	
	ArchiveIdLabel("archive_edit_archive_id", true, false),
	

	DocumentDateLabel("archive_edit_document_date", true, false ),
	
	
	TextLabel("archive_edit_text", true, false ),
	
	
	CancelButton("archive_edit_cancel_button", true, true ),
	SaveButton("archive_edit_save_button", true, true);
	
	I18NEditPageModelParts(final String key, final boolean withInForm, final boolean button ) {
		this.key=key;
		this.withInForm=withInForm;
		this.button=button;
	}
	
	private final boolean withInForm; 
	private final String key;
	private final boolean button;
	@Override
	public String wicketId() {
		return  StringUtils.uncapitalize(name());
	}
	
	public final String key() {
		return key;
	}
	
	public final boolean  isWithInForm() {
		return this.withInForm;
	}
	
	public final boolean  isButton() {
		return this.button;
	}
	
	

}
