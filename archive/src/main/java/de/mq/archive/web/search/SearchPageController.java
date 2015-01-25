package de.mq.archive.web.search;



import de.mq.archive.domain.Archive;
import de.mq.archive.web.TwoWayMapping;

interface SearchPageController {

	
	static final String SEARCH_ACTION = "search";
	
	void search(final SearchPageModel model);
	
	
	TwoWayMapping<Archive, Enum<?>> newWebModel(final Archive archive);

}