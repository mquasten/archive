package de.mq.archive.web.edit;

interface EditPageController  {
	static final String SAVE_ACTION = "save";
	void save(final EditPageModel model);
}
