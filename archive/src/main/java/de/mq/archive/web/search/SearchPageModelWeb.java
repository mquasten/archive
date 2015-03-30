package de.mq.archive.web.search;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.model.IModel;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;

public interface SearchPageModelWeb {
	TwoWayMapping<Archive, Enum<?>> getSearchCriteriaWeb();

	
	


	IModel<String> getSelectedArchiveWeb();


	boolean isSelected();

	OneWayMapping<Locale, Enum<?>> getI18NLabels();



	List<TwoWayMapping<Archive, Enum<?>>> getArchivesWeb2();





	IModel<String> getPagingInfo();





	boolean hasPaging();


	boolean isNotFirstPage();





	boolean hasNextPage();





	boolean hasPriviousPage();





	boolean isNotLastPage();





	


}
