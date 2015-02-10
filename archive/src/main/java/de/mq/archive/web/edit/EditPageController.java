package de.mq.archive.web.edit;

import de.mq.archive.web.search.SearchPageModel;

interface EditPageController  {
	static final String SAVE_ACTION = "save";
	

	void save(final EditPageModel model);


	void init(final SearchPageModel searchPageModel, final EditPageModel model);


	void init(final EditPageModel model);
}
