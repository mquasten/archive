package de.mq.archive.domain.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Profile;
import org.springframework.util.ReflectionUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;

@Named
@Profile("mock")
public class ArchiveRepositoryMock implements ArchiveRepository{

	@Override
	public List<Archive> forCriterias(Archive archive, Paging paging) {
		final List<Archive> documents = new ArrayList<>(); 
		documents.add(new ArchiveImpl("Kontoauszug Dezember" , Category.Statement, new GregorianCalendar(2014,11,1).getTime(), "ordner1/4711" ));
		documents.add(new ArchiveImpl("Gehaltsabrechnung Dezember" , Category.SalaryPrintout, new GregorianCalendar(2014,11,1).getTime(), "ordner2/4712" ));
		final Field field = ReflectionUtils.findField(ArchiveImpl.class, "id");
		field.setAccessible(true);
		documents.forEach(doc -> ReflectionUtils.setField(field, doc, String.valueOf(doc.name().hashCode()))); 
		return documents;
	}

	@Override
	public Number countForCriteria(Archive archive) {
		// TODO Auto-generated method stub
		return null;
	}

}
