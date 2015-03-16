package de.mq.archive.domain.support;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.Map.Entry;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.GridFsInfo;

public class ArchiveServiceTest {

	private static final byte[] FILE_CONTENT = "They call me the wild rose. But my name was Elisa Day. Why they call me it I do not know. For my name was Elisa Day".getBytes();
	private static final String CONTENT_TYPE = "audio/mpeg";
	private static final String FILE_NAME = "whereTheWildRosesGrow.mp3";
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
		Assert.assertEquals((long) Math.ceil((double) COUNTER / PAGE_SIZE), paging.maxPages());
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

	@Test(expected = EmptyResultDataAccessException.class)
	public final void archiveNotFound() {
		archiveService.archive(ID);
	}

	@Test
	public final void delete() {
		Mockito.when(archive.parentId()).thenReturn(Optional.of(ID));
		archiveService.delete(archive);
		Mockito.verify(archiveRepository, Mockito.times(1)).delete(archive);
		Mockito.verify(mongoFileRepository, Mockito.times(1)).deleteAll(Optional.of(ID));
	}

	@Test
	public final void upload() {
		final InputStream is = Mockito.mock(InputStream.class);
		Mockito.when(archive.parentId()).thenReturn(Optional.of(ID));
		archiveService.upload(archive, is, FILE_NAME, CONTENT_TYPE);

		Mockito.verify(mongoFileRepository, Mockito.times(1)).save(is, FILE_NAME, Optional.of(ID), CONTENT_TYPE);

	}

	@Test
	public final void attachements() {
		Mockito.when(archive.parentId()).thenReturn(Optional.of(ID));
		final Collection<GridFsInfo<String>> infos = new ArrayList<>();
		@SuppressWarnings("unchecked")
		final GridFsInfo<String> info = Mockito.mock(GridFsInfo.class);
		infos.add(info);
		Mockito.when(mongoFileRepository.resources(Optional.of(ID))).thenReturn(infos);
		final Collection<GridFsInfo<String>> results = archiveService.attachements(archive);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(info, results.stream().findFirst().get());
	}

	@Test
	public final void deleteAttachement() {
		archiveService.deleteAttachement(ID);
		Mockito.verify(mongoFileRepository).delete(ID);
	}

	@Test
	public final void content() {
		@SuppressWarnings("unchecked")
		final Entry<GridFsInfo<String>, byte[]> entry = Mockito.mock(Entry.class);
		@SuppressWarnings("unchecked")
		final GridFsInfo<String> info = Mockito.mock(GridFsInfo.class);
		Mockito.when(entry.getKey()).thenReturn(info);
		Mockito.when(entry.getValue()).thenReturn(FILE_CONTENT);
		Mockito.when(mongoFileRepository.file(ID)).thenReturn(entry);
		final Entry<GridFsInfo<String>, byte[]> result = archiveService.content(ID);
		Assert.assertEquals(info, result.getKey());
		Assert.assertEquals(FILE_CONTENT, result.getValue());
	}
}
