package de.mq.archive.web.edit;

import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.request.cycle.RequestCycle;

import de.mq.archive.web.search.SearchPageModel;

interface EditPageController  {
	static final String SAVE_ACTION = "save";
	

	void save(final EditPageModel model, final SearchPageModel searchPageModel);


	void init(final SearchPageModel searchPageModel, final EditPageModel model);


	void init(final EditPageModel model);


	void uplaod(final EditPageModel model, final FileUploadField fileUploadField);


	void deleteUpload(final EditPageModel model);


	void showAttachement(EditPageModel model, final RequestCycle requestCycle);


	void delete(EditPageModel model, final SearchPageModel searchPageModel);


	void initReadOnly(final SearchPageModel searchPageModel, final EditPageModel model);
}
