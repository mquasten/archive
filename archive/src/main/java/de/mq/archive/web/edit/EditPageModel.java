package de.mq.archive.web.edit;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.GridFsInfo;

interface EditPageModel {
	
	static final String UPLOAD_ACTION = "uploadAction";

	Archive getArchive();

	void setArchive(Archive archive);

	void add(GridFsInfo<String> attachement);

}
