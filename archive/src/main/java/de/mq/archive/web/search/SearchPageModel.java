package de.mq.archive.web.search;


import java.util.List;

import de.mq.archive.domain.Archive;

interface SearchPageModel {

	Archive getSearchCriteria();

	String getSelectedArchiveId();

	void setArchives(final List<Archive> archives);
	
	Number getPageSize();
	
}