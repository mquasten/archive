package de.mq.archive.domain.support;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;

@Named
@Singleton
public class ArchiveServiceImpl implements ArchiveService {
	
	private final ArchiveRepository archiveRepository;
	
	@Inject
	public ArchiveServiceImpl(ArchiveRepository archiveRepository) {
		this.archiveRepository = archiveRepository;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.ArchiveService#archives(de.mq.archive.domain.Archive)
	 */
	@Override
	public final List<Archive> archives(final Archive archive) {
		/*final List<Archive> documents = new ArrayList<>(); 
		documents.add(new ArchiveImpl("Kontoauszug Dezember" , Category.Statement, new GregorianCalendar(2014,11,1).getTime(), "ordner1/4711" ));
		documents.add(new ArchiveImpl("Gehaltsabrechnung Dezember" , Category.SalaryPrintout, new GregorianCalendar(2014,11,1).getTime(), "ordner2/4712" ));
		final Field field = ReflectionUtils.findField(ArchiveImpl.class, "id");
		field.setAccessible(true);
		documents.forEach(doc -> ReflectionUtils.setField(field, doc, String.valueOf(doc.name().hashCode()))); */
		
		return Collections.unmodifiableList(archiveRepository.forCriterias(archive, new SimpleResultSetPagingImpl(Integer.MAX_VALUE, Integer.MAX_VALUE)));
	}

}
