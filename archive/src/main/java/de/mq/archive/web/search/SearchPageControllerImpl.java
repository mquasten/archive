package de.mq.archive.web.search;

import java.util.Arrays;

import javax.inject.Named;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.web.BasicEnumModelImpl;
import de.mq.archive.web.EnumModel;

class SearchPageControllerImpl implements SearchPageController {
	
	private final ArchiveService archiveService;
	
	SearchPageControllerImpl(final ArchiveService archiveService) {
		this.archiveService = archiveService;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.SerachpageController#archives(de.mq.archive.domain.Archive)
	 */
	@Named("searchButton")
	@Override
	public final void search(final SearchPageModel model) {
		model.setArchivesWeb(archiveService.archives(model.getSearchCriteria()));
	}

	@Override
	public EnumModel<Archive> newWebModel(final Archive archive) {
			final EnumModel<Archive> model = new BasicEnumModelImpl<Archive>(Arrays.asList(ArchiveModelParts.values()), ArchiveImpl.class);
			model.intoWeb(archive);
			return model;
	}

}
