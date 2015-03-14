package de.mq.archive.web.edit;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.GridFsInfo;

interface EditPageModel {
	
	static final String UPLOAD_ACTION = "uploadAction";
	static final String DELETE_UPLOAD_ACTION = "deleteUploadAction";
	
	static final String SHOW_ATTACHEMENT_ACTION = "showAttachement";
	
	static final String DELETE_ACTION = "delete_document";

	Archive getArchive();

	void setArchive(Archive archive);

	void add(GridFsInfo<String> attachement);
	String getSelectedAttachementId();
	
	public boolean isPersistent();

}
