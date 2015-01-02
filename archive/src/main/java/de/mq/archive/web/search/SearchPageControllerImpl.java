package de.mq.archive.web.search;

import java.util.List;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;

class SearchPageControllerImpl implements SearchPageController {
	
	private final ArchiveService archiveService;
	
	SearchPageControllerImpl(final ArchiveService archiveService) {
		this.archiveService = archiveService;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.SerachpageController#archives(de.mq.archive.domain.Archive)
	 */
	@Override
	public final List<Archive> archives(final Archive archive) {
		return archiveService.archives(archive);
		
	}

}
