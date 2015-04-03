package de.mq.archive.web.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	@Test
	public final void firstPage() {
		final ModifyablePaging paging = Mockito.mock(ModifyablePaging.class);
		Mockito.when(searchPageModel.getPaging()).thenReturn(Optional.of(paging));
		searchPageController.firstPage(searchPageModel);
		Mockito.verify(paging).first();
	}

	@Test
	public final void firstPageNoPaging() {
		Mockito.when(searchPageModel.getPaging()).thenReturn(Optional.empty());
		searchPageController.firstPage(searchPageModel);
		Mockito.verify(paging, Mockito.never()).first();
	}

	@Test
	public final void nextPage() {
		final ModifyablePaging paging = Mockito.mock(ModifyablePaging.class);
		Mockito.when(searchPageModel.getPaging()).thenReturn(Optional.of(paging));
		searchPageController.nextPage(searchPageModel);
		Mockito.verify(paging).inc();

	}

	@Test
	public final void prevoiusPage() {
		final ModifyablePaging paging = Mockito.mock(ModifyablePaging.class);
		Mockito.when(searchPageModel.getPaging()).thenReturn(Optional.of(paging));
		searchPageController.prevoiusPage(searchPageModel);
		Mockito.verify(paging).dec();

	}

	@Test
	public final void lastPage() {
		final ModifyablePaging paging = Mockito.mock(ModifyablePaging.class);
		Mockito.when(searchPageModel.getPaging()).thenReturn(Optional.of(paging));
		searchPageController.lastPage(searchPageModel);
		Mockito.verify(paging).last();
	}

}
