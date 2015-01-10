package de.mq.archive.web.search;

import java.util.List;

import org.apache.wicket.model.IModel;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.EnumModel;

class SearchPageModelImpl implements SearchPageModel {
	
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
	
	
	
	



	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.SearchPageModel#getSearchCriteria()
	 */
	@Override
	public final Archive getSearchCriteria() {
		return searchCriteria.toDomain();
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.SearchPageModel#getSelectedArchiveId()
	 */
	@Override
	public final String getSelectedArchiveId() {
		return selectedArchive.getObject();
	}
	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.SearchPageModel#setArchivesWeb(java.util.Collection)
	 */
	@Override
	public final void setArchives(final List<Archive> archives) {
		this.archives.setObject(archives);
		
	}
	
	@Override
	public final Number getPageSize() {
		return pageSize.getObject();
	}
	
	
	
	

	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.SearchPageModel#getSearchCriteriaWeb()
	 */
	@Override
	public final EnumModel<Archive> getSearchCriteriaWeb() {
		return searchCriteria;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.SearchPageModel#getArchivesWeb()
	 */
	@Override
	public final IModel<List<Archive>> getArchivesWeb() {
		return archives;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.SearchPageModel#getSelectedArchiveWeb()
	 */
	@Override
	public final IModel<String> getSelectedArchiveWeb() {
		return selectedArchive;
	}


	@Override
	public final IModel<Number> getPageSizeWeb() {
		return pageSize;
	}


	
	
	
	
	
	

}
