package de.mq.archive.web.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.model.IModel;
import org.springframework.util.StringUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;

class SearchPageModelImpl implements SearchPageModel, SearchPageModelWeb {
	
	private final TwoWayMapping<Archive, Enum<?>> searchCriteria; 
	private final IModel<List<Archive>> archives;
	private final IModel<String> selectedArchive; 
	private final IModel<Number> pageSize; 
	
	private final OneWayMapping<Locale, Enum<?>>  labels; 
	
	SearchPageModelImpl(final TwoWayMapping<Archive, Enum<?>> searchCriteria, final OneWayMapping<Locale, Enum<?>>  labels,  final IModel<List<Archive>> archives, final IModel<String> selectedArchive, final  IModel<Number> pageSize) {
		this.searchCriteria = searchCriteria;
		this.labels=labels;
		this.archives = archives;
		this.selectedArchive = selectedArchive;
		this.pageSize = pageSize;
	}

	
	@Override
	public final Archive getSearchCriteria() {
		return searchCriteria.toDomain();
	}

	
	@Override
	public final String getSelectedArchiveId() {
		if ( StringUtils.isEmpty(selectedArchive.getObject())) {
			return "";
		}
		return selectedArchive.getObject();
	}
	
	final List<Archive> getArchives() {
		if( archives.getObject() ==null) {
			return Collections.unmodifiableList(new ArrayList<>());
		}
		return Collections.unmodifiableList(archives.getObject());
	}
	
	@Override
	public final void setArchives(final List<Archive> archives) {
		this.archives.setObject(archives);
		unSelectIfNotInResult();
	}
	
	private final void unSelectIfNotInResult() {
		
		if( getArchives().stream().filter(archive -> archive.id().equals(getSelectedArchiveId())).findFirst().isPresent() ){
			return;
		}
		selectedArchive.setObject(null);
	}
	
	@Override
	public final Number getPageSize() {
		return pageSize.getObject();
	}
	
	
	@Override
	public final TwoWayMapping<Archive, Enum<?>> getSearchCriteriaWeb() {
		return searchCriteria;
	}

	@Override
	public final IModel<List<Archive>> getArchivesWeb() {
		return archives;
	}


	@Override
	public final IModel<String> getSelectedArchiveWeb() {
		return selectedArchive;
	}


	@Override
	public final IModel<Number> getPageSizeWeb() {
		return pageSize;
	}

	@Override
	public final boolean isSelected() {
		return StringUtils.hasText(getSelectedArchiveWeb().getObject());
	}
	
	@Override
	public  final OneWayMapping<Locale, Enum<?>>  getI18NLabels() {
		return labels;
	}
	
	
	

}
