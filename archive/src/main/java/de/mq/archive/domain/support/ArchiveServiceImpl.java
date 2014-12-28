package de.mq.archive.domain.support;

import java.util.ArrayList;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Service;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.Category;

@Service
public class ArchiveServiceImpl implements ArchiveService {
	
	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.ArchiveService#archives(de.mq.archive.domain.Archive)
	 */
	@Override
	public final List<Archive> archives(final Archive archive) {
		final List<Archive> documents = new ArrayList<>(); 
		documents.add(new ArchiveImpl("Kontoauszug Dezember" , Category.Statement, new GregorianCalendar(2014,11,1).getTime() ));
		documents.add(new ArchiveImpl("Gehaltsabrechnung Dezember" , Category.SalaryPrintout, new GregorianCalendar(2014,11,1).getTime() ));
		return Collections.unmodifiableList(documents);
	}

}
