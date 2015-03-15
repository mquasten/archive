package de.mq.archive.web.search;


import java.util.List;

import de.mq.archive.domain.Archive;

public interface SearchPageModel {

	Archive getSearchCriteria();

	String getSelectedArchiveId();

	void setArchives(final List<Archive> archives);
	
	Number getPageSize();
	
	static final String INIT_EDIT = "init_edit";
	
	static final String INIT_READONLY = "init_readonly";
	
	static final String NEW_EDIT = "new_edit";
	
	static final String SEARCH_ACTION = "search";
	
	
}