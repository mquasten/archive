package de.mq.archive.web.search;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.support.ModifyablePaging;

public class SearchPageControllerTest {

	
	private static final int PAGE_SIZE = 10;
	private final ArchiveService archiveService = Mockito.mock(ArchiveService.class);
	private final SearchPageController searchPageController = new SearchPageControllerImpl(archiveService);
	private final Archive archive = Mockito.mock(Archive.class);
	private final SearchPageModel searchPageModel = Mockito.mock(SearchPageModel.class);
	private final ModifyablePaging paging = Mockito.mock(ModifyablePaging.class);
	private final List<Archive> archives = new ArrayList<>();

	@Test
	public final void search() {
		Mockito.when(searchPageModel.getSearchCriteria()).thenReturn(archive);
		Mockito.when(searchPageModel.getPageSize()).thenReturn(PAGE_SIZE);
		archives.add(archive);
		Mockito.when(archiveService.paging(archive, PAGE_SIZE)).thenReturn(paging);
		Mockito.when(archiveService.archives(archive, paging)).thenReturn(archives);
		searchPageController.search(searchPageModel);

		Mockito.verify(searchPageModel).setArchives(archives);
	}

	

}
