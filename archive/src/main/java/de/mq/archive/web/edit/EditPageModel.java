package de.mq.archive.web.edit;

import de.mq.archive.domain.Archive;

interface EditPageModel {
	
	static final String UPLOAD_ACTION = "uploadAction";

	Archive getArchive();

	void setArchive(Archive archive);

}
