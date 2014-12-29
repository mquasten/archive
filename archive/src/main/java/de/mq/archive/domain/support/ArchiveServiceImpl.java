package de.mq.archive.domain.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Named;



import javax.inject.Singleton;
import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.Category;

@Named
@Singleton
public class ArchiveServiceImpl implements ArchiveService {
	
	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.ArchiveService#archives(de.mq.archive.domain.Archive)
	 */
	@Override
	public final List<Archive> archives(final Archive archive) {
		final List<Archive> documents = new ArrayList<>(); 
		documents.add(new ArchiveImpl("Kontoauszug Dezember" , Category.Statement, new GregorianCalendar(2014,11,1).getTime(), "ordner1/4711" ));
		documents.add(new ArchiveImpl("Gehaltsabrechnung Dezember" , Category.SalaryPrintout, new GregorianCalendar(2014,11,1).getTime(), "ordner2/4712" ));
		return Collections.unmodifiableList(documents);
	}

}
