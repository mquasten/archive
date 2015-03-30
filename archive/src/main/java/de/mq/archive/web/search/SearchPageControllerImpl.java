package de.mq.archive.web.search;

import javax.inject.Named;

import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.support.ModifyablePaging;
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
		model.setPaging((ModifyablePaging) paging);
		model.setArchives(archiveService.archives(model.getSearchCriteria(), paging));
	}

	
	@Named(SearchPageModel.FIRST_PAGE_ACTION)
	@Override
	public final void firstPage(final SearchPageModel model) {
		changePaging(model, p ->  p.first());
	}
	
	@Named(SearchPageModel.NEXT_PAGE_ACTION)
	@Override
	public final void nextPage(final SearchPageModel model) {
		changePaging(model, p ->  p.inc());
	}
	
	@Named(SearchPageModel.PREVIOUS_PAGE_ACTION)
	@Override
	public final void prevoiusPage(final SearchPageModel model) {
		changePaging(model, p ->  p.dec());
	}
	
	@Named(SearchPageModel.LAST_PAGE_ACTION)
	@Override
	public final void lastPage(final SearchPageModel model) {
		changePaging(model, p ->  p.last());
	}

	private void changePaging(final SearchPageModel model, final PagingOperation pagingOperation) {
		if (! model.getPaging().isPresent() ) {
			return;
		}
		pagingOperation.change(model.getPaging().get());
			
	   model.setArchives(archiveService.archives(model.getSearchCriteria(),  model.getPaging().get()));
	}
	
}

@FunctionalInterface
interface PagingOperation  {
	void change(final ModifyablePaging paging);
}
