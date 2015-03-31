package de.mq.archive.domain.support;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.archive.domain.GridFsInfo;


public class FileRepositoryMockTest {
	
	

	private static final byte[] CONTENT = "They call me the wild rose. But my name was Elisa Day. Why they call me it I do not know. For my name was Elisa Day".getBytes();

	private static final String CONTENT_TYPE = "audio/mpeg";

	private static final String FILE_NAME = "WhereTheWildRosesGrow.mp3";

	private static final String FILES_FIELD = "files";

	private static final String DOCUMENTS_FIELD = "documents";

	private static final Optional<String> PARENT_ID = Optional.of("19680528");

	private final MongoFileRepository fileRepository = new FileRepositoryMock();
	
	
	private InputStream is = new ByteArrayInputStream(CONTENT);
	
	
	@Test
	public final void save() {
		final Map<String,Map<String,GridFsInfo<String>>>  documents = documents();
		final Map<String, byte[]> files = files();
		Assert.assertEquals(0, documents.size());
		Assert.assertEquals(0, files.size());
		fileRepository.save(is, FILE_NAME, PARENT_ID, CONTENT_TYPE);
		final Map<String, GridFsInfo<String>> results = documents.get(PARENT_ID.get());
		Assert.assertEquals(1, documents.size());
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(FILE_NAME.toLowerCase(), results.keySet().stream().findFirst().get());
		final GridFsInfo<String> result = results.values().stream().findFirst().get();
		Assert.assertEquals(FILE_NAME, result.filename());
		Assert.assertEquals( new UUID(PARENT_ID.get().hashCode(),FILE_NAME.toLowerCase().hashCode()).toString(), result.id());
		Assert.assertTrue(100 > new Date().getTime() - result.lastModified().getTime() );
		Assert.assertEquals(CONTENT_TYPE, result.contentType());
		Assert.assertEquals(CONTENT.length, result.contentLength());
		Assert.assertEquals(1, files.size());
		Assert.assertEquals(new String(CONTENT), new String(files.values().stream().findFirst().get()));
		Assert.assertEquals(result.id(), files.keySet().stream().findFirst().get());
	}
	
	@Test
	public final void saveReplace() {
		@SuppressWarnings("unchecked")
		final GridFsInfo<String> info = Mockito.mock(GridFsInfo.class);
		Mockito.when(info.id()).thenReturn(new UUID(PARENT_ID.get().hashCode(), FILE_NAME.toLowerCase().hashCode()).toString());
		final Map<String,Map<String,GridFsInfo<String>>>  documents = documents();
		final Map<String, byte[]> files = files();
		final Map<String, GridFsInfo<String>> infos = new HashMap<>();
		infos.put(FILE_NAME.toLowerCase(), info);
		
		documents.put(PARENT_ID.get(), infos);
		files.put(info.id(), CONTENT);
		Assert.assertEquals(1, documents.size());
		Assert.assertEquals(1, documents.get(PARENT_ID.get()).size());
		Assert.assertEquals(1, files.size());
		
		final byte[]  content = "I just can't get you out of my head. Boy your loving is all I think about. I just can't get you out of my head. Boy it's more than I dare to think about. La la la la la la la la".getBytes();
		InputStream is = new ByteArrayInputStream( content);
		String fileName = "CanTGetYouOutOfMyHead.mp3";
		fileRepository.save(is, fileName, PARENT_ID, CONTENT_TYPE);
		Assert.assertEquals(1, documents.size());
		
		final Map<String,GridFsInfo<String>> results = documents.get(PARENT_ID.get());
		Assert.assertEquals(info, results.get(FILE_NAME.toLowerCase()));
		Assert.assertEquals(CONTENT, files.get(info.id()));
		
		final GridFsInfo<String> result = results.get(fileName.toLowerCase());
		Assert.assertEquals(fileName, result.filename());
		Assert.assertEquals(new UUID(PARENT_ID.get().hashCode(), fileName.toLowerCase().hashCode()).toString(), result.id());
		Assert.assertEquals(CONTENT_TYPE, result.contentType());
		Assert.assertEquals(content.length, result.contentLength());
		Assert.assertTrue(100 > new Date().getTime() - result.lastModified().getTime() );
		Assert.assertEquals(2, files.size());
		Assert.assertEquals(new String(content), new String(files.get(result.id())));
		
			
	}


	@SuppressWarnings("unchecked")
	private Map<String, byte[]> files() {
		return (Map<String, byte[]>) ReflectionTestUtils.getField(fileRepository, FILES_FIELD);
	}


	@SuppressWarnings("unchecked")
	private Map<String,Map<String,GridFsInfo<String>>> documents() {
		 return  (Map<String, Map<String, GridFsInfo<String>>>) ReflectionTestUtils.getField(fileRepository, DOCUMENTS_FIELD);
	}

}
