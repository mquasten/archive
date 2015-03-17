package de.mq.archive.domain.support;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;






import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFSFile;

public class MongoFileRepositoryTest {
	
	private final GridFsOperations gridOperations = Mockito.mock(GridFsOperations.class);
	
	private final MongoFileRepository repository = new MongoFileRepositoryImpl(gridOperations);
	
	private InputStream is = Mockito.mock(InputStream.class);
	private static final String NAME="whereTheWildRosesGrow.mp3"; 
	private static final Optional<String> PARENT_ID=Optional.of("19680528");
	private static final String CONTENT_TYPE="audio/mpeg"; 
	private static final  GridFSFile gridFSFile = Mockito.mock(GridFSFile.class);
	
	@Test
	public final void save() {
		Mockito.when(gridOperations.store(is, NAME.trim(), CONTENT_TYPE,  PARENT_ID)).thenReturn(gridFSFile);
		repository.save(is, NAME, PARENT_ID, CONTENT_TYPE);
		
		final ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		Mockito.verify(gridOperations).delete(queryCaptor.capture());
		@SuppressWarnings("unchecked")
		final Map<String,Object> criterias = (((BasicDBObject) queryCaptor.getValue().getQueryObject()).toMap());
		Assert.assertEquals(PARENT_ID, criterias.get(MongoFileRepositoryImpl.METADATA_FIELD));
		Assert.assertEquals(NAME.toLowerCase(), criterias.get(MongoFileRepositoryImpl.ALIASES_FIELD));
		Mockito.verify(gridFSFile).put(MongoFileRepositoryImpl.ALIASES_FIELD, Stream.of(NAME.toLowerCase()).toArray());
		Mockito.verify(gridFSFile).save();
	
	}
	
	
	

}
