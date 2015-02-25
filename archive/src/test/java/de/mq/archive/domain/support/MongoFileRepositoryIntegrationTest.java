package de.mq.archive.domain.support;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

import com.mongodb.gridfs.GridFSDBFile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/mongo-test.xml"})
@ActiveProfiles({"db"})
public class MongoFileRepositoryIntegrationTest {
	private static final String FILE_NAME = "WhereTheWildRosesGrow.mp3";
	@Inject
	private  GridFsOperations gridOperations;
	
	@Ignore
	@Test
	public final void store() throws FileNotFoundException {
		System.out.println(gridOperations);
		final InputStream is = new ByteArrayInputStream("They call me The Wild Rose But my name was Elisa Day Why they call me it I do not know For my name was ...".getBytes());
	
		gridOperations.store(is, FILE_NAME, "audio/mpeg" , new FileMetaInfos());
	}
	
	@Test
	public final void read() {
		final Query query = new Query(new Criteria("metadata").is(new FileMetaInfos()));
		
		final Collection<GridFSDBFile> results = gridOperations.find(query);
		
		
		
		results.forEach(x -> System.out.println(print(x) ) );
	

}

private String print(final GridFSDBFile info) {
	
	try {
		return info.getId() + ":" + new String( FileCopyUtils.copyToByteArray(info.getInputStream()))+":"+ info.getMetaData().toString() ;
	} catch (IOException e) {
		return "sucks:" +e.getMessage();
	}
}

}
class FileMetaInfos {
	
	
	private String parentId = "19680528";

	@Override
	public int hashCode() {
		
		return parentId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FileMetaInfos)) {
			return false;
		}
		final FileMetaInfos  other = (FileMetaInfos) obj;
		return parentId.equals(other);
	}

	
	
	
	
}