package de.mq.archive.domain.support;

import java.util.ArrayList;
import java.util.List;






import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;

public class ArchiveServiceTest {
	
	private static final String ID = "19680528";
	private static final int COUNTER = 42;
	private static final int PAGE_SIZE = 10;
	private final ArchiveRepository archiveRepository = Mockito.mock(ArchiveRepository.class);
	private final MongoFileRepository mongoFileRepository = Mockito.mock(MongoFileRepository.class);
	private final ArchiveService archiveService = new ArchiveServiceImpl(archiveRepository, mongoFileRepository);
	private final Archive archive = Mockito.mock(Archive.class);
	private final Paging paging = Mockito.mock(Paging.class);

	
	
	
	@Test
	public final void archives() {
		final List<Archive> archives = new ArrayList<>();
		archives.add(archive);
		Mockito.when(archiveRepository.forCriterias(archive, paging)).thenReturn(archives);
		Assert.assertEquals(archives, archiveService.archives(archive, paging));
	}
	
	@Test
	public final void paging() {
		Mockito.when(archiveRepository.countForCriteria(archive)).thenReturn(COUNTER);
		final Paging paging = archiveService.paging(archive, PAGE_SIZE);
		Assert.assertEquals(PAGE_SIZE, paging.pageSize());
		Assert.assertEquals((long) Math.ceil((double) COUNTER/PAGE_SIZE), paging.maxPages());
	}
	
	@Test
	public final void save() {
		archiveService.save(archive);
		Mockito.verify(archiveRepository).save(archive);
	}
	
	@Test
	public final void archive() {
		Mockito.when(archiveRepository.forId(ID)).thenReturn(archive);
		archiveService.archive(ID);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public final void archiveNotFound() {
		archiveService.archive(ID);
	}

}
