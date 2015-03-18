package de.mq.archive.domain.support;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.web.client.ResourceAccessException;

import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import de.mq.archive.domain.GridFsInfo;

public class MongoFileRepositoryTest {

	private static final String CONTENT = "They call me the wild rose...";

	private static final Date DATE = new GregorianCalendar(1968, 4, 28).getTime();

	private static final String ID = UUID.randomUUID().toString();

	private static final long LENGTH = 42L;

	private final GridFsOperations gridOperations = Mockito.mock(GridFsOperations.class);

	private final MongoFileRepository repository = new MongoFileRepositoryImpl(gridOperations);

	private InputStream is = Mockito.mock(InputStream.class);
	private static final String NAME = "whereTheWildRosesGrow.mp3";
	private static final Optional<String> PARENT_ID = Optional.of("19680528");
	private static final String CONTENT_TYPE = "audio/mpeg";
	private static final GridFSFile gridFSFile = Mockito.mock(GridFSFile.class);

	@Test
	public final void save() {
		Mockito.when(gridOperations.store(is, NAME.trim(), CONTENT_TYPE, PARENT_ID)).thenReturn(gridFSFile);
		repository.save(is, NAME, PARENT_ID, CONTENT_TYPE);

		final ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		Mockito.verify(gridOperations).delete(queryCaptor.capture());
		@SuppressWarnings("unchecked")
		final Map<String, Object> criterias = (((BasicDBObject) queryCaptor.getValue().getQueryObject()).toMap());
		Assert.assertEquals(PARENT_ID, criterias.get(MongoFileRepositoryImpl.METADATA_FIELD));
		Assert.assertEquals(NAME.toLowerCase(), criterias.get(MongoFileRepositoryImpl.ALIASES_FIELD));
		Mockito.verify(gridFSFile).put(MongoFileRepositoryImpl.ALIASES_FIELD, Stream.of(NAME.toLowerCase()).toArray());
		Mockito.verify(gridFSFile).save();

	}

	@Test
	public final void resources() {
		final List<GridFSDBFile> results = new ArrayList<>();
		final GridFSDBFile gridFSDBFile = newGridFSDBFile(ID);
		final GridFSDBFile gridFSDBFileWithoutId = newGridFSDBFile(null);

		results.add(gridFSDBFile);
		results.add(gridFSDBFileWithoutId);
		Mockito.when(gridOperations.find(Mockito.any(Query.class))).thenReturn(results);

		final Collection<GridFsInfo<String>> resources = repository.resources(PARENT_ID);
		final ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		Mockito.verify(gridOperations).find(queryCaptor.capture());
		@SuppressWarnings("unchecked")
		final Map<String, Object> criterias = (((BasicDBObject) queryCaptor.getValue().getQueryObject()).toMap());
		Assert.assertEquals(1, criterias.size());
		Assert.assertEquals(PARENT_ID, criterias.get(MongoFileRepositoryImpl.METADATA_FIELD));
		Assert.assertEquals(2, resources.size());
		resources.forEach(r -> {
			if (r.id() != null) {
				Assert.assertEquals(gridFSDBFile.getId(), r.id());
			}
			Assert.assertEquals(gridFSDBFile.getLength(), r.contentLength());
			Assert.assertEquals(gridFSDBFile.getContentType(), r.contentType());

			Assert.assertEquals(gridFSDBFile.getFilename(), r.filename());
			Assert.assertEquals(gridFSDBFile.getUploadDate(), r.lastModified());

		});

	}

	private GridFSDBFile newGridFSDBFile(final String id) {
		final GridFSDBFile gridFSDBFile = Mockito.mock(GridFSDBFile.class);
		Mockito.when(gridFSDBFile.getId()).thenReturn(id);
		Mockito.when(gridFSDBFile.getFilename()).thenReturn(NAME);
		Mockito.when(gridFSDBFile.getContentType()).thenReturn(CONTENT_TYPE);
		Mockito.when(gridFSDBFile.getLength()).thenReturn(LENGTH);
		Mockito.when(gridFSDBFile.getUploadDate()).thenReturn(DATE);
		return gridFSDBFile;
	}

	@Test
	public final void deleteAll() {
		repository.deleteAll(PARENT_ID);
		final ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		Mockito.verify(gridOperations).delete(queryCaptor.capture());
		@SuppressWarnings("unchecked")
		final Map<String, Object> criterias = (((BasicDBObject) queryCaptor.getValue().getQueryObject()).toMap());
		Assert.assertEquals(1, criterias.size());
		Assert.assertEquals(PARENT_ID, criterias.get(MongoFileRepositoryImpl.METADATA_FIELD));
	}

	@Test
	public final void delete() {
		repository.delete(ID);
		final ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		Mockito.verify(gridOperations).delete(queryCaptor.capture());
		@SuppressWarnings("unchecked")
		final Map<String, Object> criterias = (((BasicDBObject) queryCaptor.getValue().getQueryObject()).toMap());
		Assert.assertEquals(1, criterias.size());
		Assert.assertEquals(ID, criterias.get(MongoFileRepositoryImpl.ID_FIELD));
	}

	@Test
	public void file() {
		final GridFSDBFile gridFSDBFile = newGridFSDBFile(ID);
		Mockito.when(gridFSDBFile.getInputStream()).thenReturn(new ByteArrayInputStream(CONTENT.getBytes()));
		Mockito.when(gridOperations.findOne(Mockito.any(Query.class))).thenReturn(gridFSDBFile);
		final Entry<GridFsInfo<String>, byte[]> result = repository.file(ID);
		final ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		Mockito.verify(gridOperations).findOne(queryCaptor.capture());
		@SuppressWarnings("unchecked")
		final Map<String, Object> criterias = (((BasicDBObject) queryCaptor.getValue().getQueryObject()).toMap());
		Assert.assertEquals(1, criterias.size());
		Assert.assertEquals(ID, criterias.get(MongoFileRepositoryImpl.ID_FIELD));

		Assert.assertEquals(gridFSDBFile.getId(), result.getKey().id());
		Assert.assertEquals(gridFSDBFile.getLength(), result.getKey().contentLength());
		Assert.assertEquals(gridFSDBFile.getContentType(), result.getKey().contentType());
		Assert.assertEquals(gridFSDBFile.getFilename(), result.getKey().filename());
		Assert.assertEquals(gridFSDBFile.getUploadDate(), result.getKey().lastModified());
		Assert.assertEquals(CONTENT, new String(result.getValue()));
	}

	@Test(expected = ResourceAccessException.class)
	public void fileSucks() throws IOException {
		final GridFSDBFile gridFSDBFile = newGridFSDBFile(ID);
		InputStream is = Mockito.mock(InputStream.class);
		Mockito.when(is.read(Mockito.any())).thenThrow(new IOException("Don't worry only for test"));
		Mockito.when(gridFSDBFile.getInputStream()).thenReturn(is);
		Mockito.when(gridOperations.findOne(Mockito.any(Query.class))).thenReturn(gridFSDBFile);
		repository.file(ID);
	}

	@Test(expected = ResourceAccessException.class)
	public void fileSucks2() throws IOException {
		final GridFSDBFile gridFSDBFile = newGridFSDBFile(ID);
		InputStream is = Mockito.mock(InputStream.class);
		Mockito.doThrow(IOException.class).when(is).close();
		Mockito.when(is.read(Mockito.any())).thenReturn(-1);
		Mockito.when(gridFSDBFile.getInputStream()).thenReturn(is);
		Mockito.when(gridOperations.findOne(Mockito.any(Query.class))).thenReturn(gridFSDBFile);
		repository.file(ID);
	}

}
