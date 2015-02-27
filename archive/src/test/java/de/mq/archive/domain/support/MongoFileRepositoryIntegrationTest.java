package de.mq.archive.domain.support;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;








import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.mq.archive.domain.GridFsInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/mongo-test.xml"})
@ActiveProfiles({"db"})
public class MongoFileRepositoryIntegrationTest {
	
	private static final String PARENT_ID = "kylieAndNick";

	private static final byte[] CONTENT = "They call me The Wild Rose But my name was Elisa Day Why they call me it I do not know. For my name was Elisa Day ...".getBytes();

	private static final String CONTENT_TYPE = "audio/mpeg";

	private static final String NAME = "whereTheWildRosesGrow";

	@Autowired
	private MongoFileRepository mongoFileRepository;
	
	private final InputStream is = new ByteArrayInputStream( CONTENT );
	
	@Test
	public final void save() {
		mongoFileRepository.save(is, NAME,  Optional.of(PARENT_ID),  CONTENT_TYPE);
		
		final Collection<GridFsInfo<String>> results = mongoFileRepository.resources(Optional.of(PARENT_ID));
		Assert.assertEquals(1, results.size());
		
		final GridFsInfo<String> result = results.stream().findFirst().get();
		Assert.assertNotNull(result.id());
		Assert.assertEquals(NAME, result.filename());
		Assert.assertEquals(CONTENT_TYPE, result.contentType());
		Assert.assertEquals((long) CONTENT.length, result.contentLength());
		
	}

}
