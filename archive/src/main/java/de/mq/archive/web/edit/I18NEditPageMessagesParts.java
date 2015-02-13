package de.mq.archive.web.edit;

import org.springframework.util.StringUtils;

import de.mq.archive.web.search.WicketIdAware;

public enum I18NEditPageMessagesParts implements WicketIdAware {

	
	DocumentDate("archive_edit_document_date_message"), 
	Category("archive_edit_mandatory_message"),
	ArchiveId("archive_edit_archive_id_message"),
	Name("archive_edit_name_message");
	
	static final String FEEDBACK = "Feedback";
	static final String MESSAGE = "Message";
	private String key;
	
	I18NEditPageMessagesParts( final String key){
		this.key=key;
	}
	
	@Override
	public String wicketId() {
		 return  StringUtils.uncapitalize(name() +MESSAGE);
	}
	
	public String wicketIdFeedback() {
		 return  StringUtils.uncapitalize(name() +FEEDBACK);
	}
	
	public String wicketIdInput() {
		 return  StringUtils.uncapitalize(name() );
	}
	
	public final String key() {
		return this.key;
	}
}
