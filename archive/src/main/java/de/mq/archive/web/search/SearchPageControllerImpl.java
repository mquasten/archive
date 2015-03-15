package de.mq.archive.web.search;

import javax.inject.Named;

import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.support.Paging;

class SearchPageControllerImpl implements SearchPageController {
	
	
	private final ArchiveService archiveService;
	
	SearchPageControllerImpl(final ArchiveService archiveService) {
		this.archiveService = archiveService;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.SerachpageController#archives(de.mq.archive.domain.Archive)
	 */
	@Named(SearchPageModel.SEARCH_ACTION)
	@Override
	public final void search(final SearchPageModel model) {
		final Paging paging = archiveService.paging(model.getSearchCriteria(), model.getPageSize().intValue());
		model.setArchives(archiveService.archives(model.getSearchCriteria(), paging));
	}

	

}
