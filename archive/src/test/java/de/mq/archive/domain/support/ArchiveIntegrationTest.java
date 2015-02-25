package de.mq.archive.domain.support;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/mongo-test.xml"})
@ActiveProfiles({"db"})
@Ignore
public class ArchiveIntegrationTest {
	
	@Inject
	private MongoOperations mongoOperations;
	
	@Inject
	private ArchiveRepository archiveRepository;
	
	@Test
	@Ignore
	public final void create() {
		
		final List<Archive> archives = new ArrayList<>();
		
		archives.add(new ArchiveImpl("Kontoauszug Dezember 2014",Category.Statement,new Date(),"4711/12" ));
		archives.add(new ArchiveImpl("Gehaltsabrechnung Dezember 2014",Category.SalaryPrintout,new Date(),"4712-/12" ));
		archives.add(new ArchiveImpl("Love letter for Kylie",Category.Correspondence,new Date(),"19680528", "Where the wild roses grow ..." ));
		
		archives.forEach(archive -> mongoOperations.save(archive));
		
	}
	
	@Test
	@Ignore
	public final void search() {
		final Archive archive =  BeanUtils.instantiateClass(ArchiveImpl.class);
		final Paging paging = new SimpleResultSetPagingImpl( Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		final List<Archive> results = archiveRepository.forCriterias(archive, paging);
		results.forEach(a -> System.out.println(a.name()));
		
		Assert.assertEquals(   results.size(), archiveRepository.countForCriteria(archive).intValue());
	}


}
