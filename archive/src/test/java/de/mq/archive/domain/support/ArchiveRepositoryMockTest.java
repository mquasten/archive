package de.mq.archive.domain.support;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;

public class ArchiveRepositoryMockTest {

	private static final String NAME = "Where the wild roses grow";
	private static final String ARCHIVES_FIELD = "archives";
	private static final String FORMAT_PATTERN = "name %02d";
	private final ArchiveRepository archiveRepository = new ArchiveRepositoryMock();

	@Test
	public final void init() {
		@SuppressWarnings("unchecked")
		final Map<String, Archive> archives = (Map<String, Archive>) ReflectionTestUtils.getField(archiveRepository, ARCHIVES_FIELD);
		Assert.assertTrue(archives.isEmpty());
		((ArchiveRepositoryMock) archiveRepository).init();
		Assert.assertEquals(2, archives.size());

		Assert.assertEquals(ArchiveRepositoryMock.ARCHIVE_01, archives.get(ArchiveRepositoryMock.ARCHIVE_01.id()));
		Assert.assertEquals(ArchiveRepositoryMock.ARCHIVE_02, archives.get(ArchiveRepositoryMock.ARCHIVE_02.id()));
	}

	@Test
	public final void forCriterias() {
		((ArchiveRepositoryMock) archiveRepository).init();
		final Collection<Archive> results = archiveRepository.forCriterias(ArchiveRepositoryMock.ARCHIVE_01, newPaging());
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(ArchiveRepositoryMock.ARCHIVE_01, results.iterator().next());
	}

	private Paging newPaging() {
		return newPaging(0, 10);
	}

	private Paging newPaging(final int first, final int size) {
		final Paging paging = Mockito.mock(Paging.class);
		Mockito.when(paging.pageSize()).thenReturn(size);
		Mockito.when(paging.firstRow()).thenReturn(first);
		return paging;
	}

	@Test
	public final void forCriteriasAllMatching() {
		final Paging paging = newPaging();
		final Archive archive = Mockito.mock(Archive.class);
		((ArchiveRepositoryMock) archiveRepository).init();
		final Collection<Archive> results = archiveRepository.forCriterias(archive, paging);
		Assert.assertEquals(2, results.size());
		Assert.assertTrue(results.contains(ArchiveRepositoryMock.ARCHIVE_01));
		Assert.assertTrue(results.contains(ArchiveRepositoryMock.ARCHIVE_02));

	}

	@Test
	public final void forCriteriasMatchingCategory() {
		final Paging paging = newPaging();
		final Archive archive = Mockito.mock(Archive.class);
		Mockito.when(archive.category()).thenReturn(Category.Statement);
		((ArchiveRepositoryMock) archiveRepository).init();
		final Collection<Archive> results = archiveRepository.forCriterias(archive, paging);
		Assert.assertEquals(1, results.size());
		Assert.assertTrue(results.contains(ArchiveRepositoryMock.ARCHIVE_01));

	}

	@Test
	public final void forCriteriasMatchingMatchingArchiveId() {
		final Paging paging = newPaging();
		final Archive archive = Mockito.mock(Archive.class);
		Mockito.when(archive.archiveId()).thenReturn(ArchiveRepositoryMock.ARCHIVE_01.archiveId());
		((ArchiveRepositoryMock) archiveRepository).init();
		final Collection<Archive> results = archiveRepository.forCriterias(archive, paging);
		Assert.assertEquals(1, results.size());
		Assert.assertTrue(results.contains(ArchiveRepositoryMock.ARCHIVE_01));
	}

	@Test
	public final void paging() {
		@SuppressWarnings("unchecked")
		final Map<String, Archive> archives = (Map<String, Archive>) ReflectionTestUtils.getField(archiveRepository, ARCHIVES_FIELD);
		IntStream.range(0, 20).forEach(counter -> archives.put(UUID.randomUUID().toString(), new ArchiveImpl(String.format(FORMAT_PATTERN, counter), Category.Correspondence, new Date(), String.format("archive %s", counter))));
		final Archive archive = Mockito.mock(Archive.class);

		final Collection<String> results = archiveRepository.forCriterias(archive, newPaging(5, 10)).stream().map(arch -> arch.name()).collect(Collectors.toList());

		Assert.assertEquals(10, results.size());
		IntStream.range(5, 15).forEach(i -> Assert.assertTrue(results.contains(String.format(FORMAT_PATTERN, i))));
	}
	
	@Test
	public final void countForCriteria() {
		((ArchiveRepositoryMock) archiveRepository).init();
		
		Assert.assertEquals(((Map<?, ?>) ReflectionTestUtils.getField(archiveRepository, ARCHIVES_FIELD)).size(), archiveRepository.countForCriteria(Mockito.mock(Archive.class)));
	}
	
	@Test
	public final void save() {
		@SuppressWarnings("unchecked")
		final Map<String, Archive> archives = (Map<String, Archive>) ReflectionTestUtils.getField(archiveRepository, ARCHIVES_FIELD);
		final Archive archive = new ArchiveImpl(NAME, Category.Correspondence, null, null);
	
		archiveRepository.save(archive);
		Assert.assertEquals(1, archives.size());
		Assert.assertEquals(archive, archives.get(String.valueOf(UUID.nameUUIDFromBytes(archive.name().getBytes()))));
		Assert.assertEquals(String.valueOf(UUID.nameUUIDFromBytes(NAME.getBytes())), archive.id());
	}
	
	@Test
	public final void forId() {
		((ArchiveRepositoryMock) archiveRepository).init();
		Assert.assertEquals(ArchiveRepositoryMock.ARCHIVE_01, archiveRepository.forId(ArchiveRepositoryMock.ARCHIVE_01.id()));
	}

}
