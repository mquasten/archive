package de.mq.archive.domain.support;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ArchiveTest.class, ArchiveRepositoryTest.class, PagingTest.class, ArchiveServiceTest.class, ArchiveRepositoryMockTest.class, 
	CategoryTest.class, GridFsInfoTest.class, MongoFileRepositoryTest.class, FileRepositoryMockTest.class })
public class DomainTests {

}
