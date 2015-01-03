package de.mq.archive.web.search;

import java.util.Arrays;
import java.util.List;



import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.web.BasicEnumModelImpl;
import de.mq.archive.web.EnumModel;

class SearchPageModelImpl implements SearchPageModel {
	
	private final EnumModel<Archive> searchCriteria = new BasicEnumModelImpl<Archive>(Arrays.asList(ArchiveModelParts.values()), ArchiveImpl.class);

	private final IModel<List<Archive>> archives = new ListModel<>();
	
	private final IModel<String> selectedArchive = new Model<>();
	
	SearchPageModelImpl() {
		
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
	public final void setArchivesWeb(final List<Archive> archives) {
		this.archives.setObject(archives);
		
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

	
	
	
	
	
	

}
