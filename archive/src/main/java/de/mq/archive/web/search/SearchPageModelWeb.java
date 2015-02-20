package de.mq.archive.web.search;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.model.IModel;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;

public interface SearchPageModelWeb {
	TwoWayMapping<Archive, Enum<?>> getSearchCriteriaWeb();

	IModel<List<Archive>> getArchivesWeb();

	IModel<String> getSelectedArchiveWeb();

	IModel<Number> getPageSizeWeb();

	boolean isSelected();

	OneWayMapping<Locale, Enum<?>> getI18NLabels();


}
