package de.mq.archive.web.search;



import de.mq.archive.domain.Archive;
import de.mq.archive.web.EnumModel;

interface SearchPageController {

	
	static final String SEARCH_ACTION = "search";
	
	void search(final SearchPageModel model);
	
	
	EnumModel<Archive> newWebModel(final Archive archive);

}