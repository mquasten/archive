package de.mq.archive.web.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.Category;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.domain.support.ModifyablePaging;
import de.mq.archive.web.TwoWayMapping;

public class SearchPageControllerTest {

	private static final String ID_FIELD = "id";
	private static final String ID = "LettersId";
	private static final String ARCHIVE_ID = "19680528";
	private static final Date DOCUMENT_DATE = new Date();
	private static final Category CATEGORY = Category.Correspondence;
	private static final String NAME = "Loveletter for Kylie";
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
	public final void newWebModel() {
		final Archive archive = new ArchiveImpl(NAME, CATEGORY, DOCUMENT_DATE, ARCHIVE_ID);
		ReflectionTestUtils.setField(archive, ID_FIELD, ID);
		final TwoWayMapping<Archive, Enum<?>>results = searchPageController.newWebModel(archive);
		Assert.assertEquals(ID, results.toDomain().id());
		Assert.assertEquals(NAME, results.toDomain().name());
		Assert.assertEquals(CATEGORY, results.toDomain().category());
		Assert.assertEquals(DOCUMENT_DATE, results.toDomain().documentDate());
		Assert.assertEquals(ARCHIVE_ID, results.toDomain().archiveId());
	}

}
