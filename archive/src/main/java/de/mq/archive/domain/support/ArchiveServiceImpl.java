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
	public final List<Archive> archives(final Archive archive, final Paging paging) {
		return Collections.unmodifiableList(archiveRepository.forCriterias(archive, new SimpleResultSetPagingImpl(Integer.MAX_VALUE, Integer.MAX_VALUE)));
	}
	
	@Override
	public final ModifyablePaging paging(final Archive archive,  final int pageSize) {
		final Number counter = archiveRepository.countForCriteria(archive);
		return new SimpleResultSetPagingImpl(pageSize, counter.longValue());
	}

}
