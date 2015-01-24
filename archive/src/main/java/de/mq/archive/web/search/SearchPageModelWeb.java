package de.mq.archive.web.search;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.model.IModel;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.EnumModel;
import de.mq.archive.web.OneWayMapping;

interface SearchPageModelWeb {
	EnumModel<Archive> getSearchCriteriaWeb();

	IModel<List<Archive>> getArchivesWeb();

	IModel<String> getSelectedArchiveWeb();

	IModel<Number> getPageSizeWeb();

	boolean isSelected();

	OneWayMapping<Locale, Enum<?>> getI18NLabels();


}
