package de.mq.archive.web.search;



import de.mq.archive.domain.Archive;
import de.mq.archive.web.EnumModel;

interface SearchPageController {


	void search(final SearchPageModel model);
	
	
	EnumModel<Archive> newWebModel(final Archive archive);

}