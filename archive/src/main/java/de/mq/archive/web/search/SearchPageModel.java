package de.mq.archive.web.search;


import java.util.List;
import java.util.Optional;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.support.ModifyablePaging;

public interface SearchPageModel {
	
static final String INIT_EDIT = "init_edit";
	
	static final String INIT_READONLY = "init_readonly";
	
	static final String NEW_EDIT = "new_edit";
	
	static final String SEARCH_ACTION = "search";
	
	static final String FIRST_PAGE_ACTION = "firstPage";
	static final String NEXT_PAGE_ACTION = "nextPage";
	static final String PREVIOUS_PAGE_ACTION = "previousPage";
	static final String LAST_PAGE_ACTION = "lastPage";
	

	Archive getSearchCriteria();

	String getSelectedArchiveId();

	void setArchives(final List<Archive> archives);
	
	Number getPageSize();
	
	
	Optional<ModifyablePaging> getPaging();

	void setPaging(ModifyablePaging paging);
	
	@FunctionalInterface
	interface PagingOperation<T>  {
		T execute(final ModifyablePaging paging);
	}
	
}