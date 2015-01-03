package de.mq.archive.web.search;


import java.util.List;

import org.apache.wicket.model.IModel;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.EnumModel;

interface SearchPageModel {

	Archive getSearchCriteria();

	String getSelectedArchiveId();

	void setArchivesWeb(final List<Archive> archives);
	

	EnumModel<Archive> getSearchCriteriaWeb();

	IModel<List<Archive>> getArchivesWeb();

	IModel<String> getSelectedArchiveWeb();

}