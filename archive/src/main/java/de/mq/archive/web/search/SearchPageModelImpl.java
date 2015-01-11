package de.mq.archive.web.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.springframework.util.StringUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.EnumModel;

class SearchPageModelImpl implements SearchPageModel, SearchPageModelWeb {
	
	private final EnumModel<Archive> searchCriteria; 
	private final IModel<List<Archive>> archives;
	private final IModel<String> selectedArchive; 
	private final IModel<Number> pageSize; 
	
	SearchPageModelImpl(final EnumModel<Archive> searchCriteria, final IModel<List<Archive>> archives, final IModel<String> selectedArchive, final  IModel<Number> pageSize) {
		this.searchCriteria = searchCriteria;
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
	public final EnumModel<Archive> getSearchCriteriaWeb() {
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


	
	
	
	
	
	

}
