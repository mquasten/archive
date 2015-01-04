package de.mq.archive.domain.support;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/mongo-test.xml"})
@Ignore
public class ArchiveIntegrationTest {
	
	@Inject
	private MongoOperations mongoOperations;
	
	@Test
	@Ignore
	public final void test() {
		System.out.println(mongoOperations);
		final List<Archive> archives = new ArrayList<>();
		
		archives.add(new ArchiveImpl("Kontoauszug Dezember 2014",Category.Statement,new Date(),"4711/12" ));
		archives.add(new ArchiveImpl("Gehaltsabrechnung Dezember 2014",Category.SalaryPrintout,new Date(),"4712-/12" ));
		archives.add(new ArchiveImpl("Love letter for Kylie",Category.Correspondence,new Date(),"19680528", "Where the wild roses grow ..." ));
		
		archives.forEach(archive -> mongoOperations.save(archive));
		
		System.out.println("startet");
	}


}
