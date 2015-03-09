package de.mq.archive.web.edit;

import org.apache.wicket.markup.html.form.upload.FileUploadField;

import de.mq.archive.web.search.SearchPageModel;

interface EditPageController  {
	static final String SAVE_ACTION = "save";
	

	void save(final EditPageModel model);


	void init(final SearchPageModel searchPageModel, final EditPageModel model);


	void init(final EditPageModel model);


	void uplod(final EditPageModel model, final FileUploadField fileUploadField);


	void deleteUpload(final EditPageModel model);
}
