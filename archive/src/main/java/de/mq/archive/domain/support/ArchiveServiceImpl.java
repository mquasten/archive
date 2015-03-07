package de.mq.archive.domain.support;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.dao.EmptyResultDataAccessException;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;


@Named
@Singleton
public class ArchiveServiceImpl implements ArchiveService {
	
	
	
	private final ArchiveRepository archiveRepository;
	private final MongoFileRepository fileRepository;
	
	@Inject
	public ArchiveServiceImpl(final ArchiveRepository archiveRepository, final MongoFileRepository fileRepository) {
		this.archiveRepository = archiveRepository;
		this.fileRepository=fileRepository;
	}
	
	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.ArchiveService#archives(de.mq.archive.domain.Archive)
	 */
	@Override
	public final List<Archive> archives(final Archive archive, final Paging paging) {
		return Collections.unmodifiableList(archiveRepository.forCriterias(archive, paging));
	}
	
	@Override
	public final ModifyablePaging paging(final Archive archive,  final int pageSize) {
		final Number counter = archiveRepository.countForCriteria(archive);
		return new SimpleResultSetPagingImpl(pageSize, counter.longValue());
	}

	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.domain.ArchiveService#save(de.mq.archive.domain.Archive)
	 */
	@Override
	public void save(final Archive archive) {
		archiveRepository.save(archive);
	}
	
	@Override
	public Archive archive(final String id) {
		final Archive archive = archiveRepository.forId(id);
		if( archive == null){
			throw new EmptyResultDataAccessException("Archive not found", 1);	
		}
		return archive;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.domain.ArchiveService#upload(de.mq.archive.domain.Archive, java.io.InputStream, java.lang.String, java.lang.String)
	 */
	@Override
	public void upload(final  Archive archive, final InputStream is , final String filename, final String contentType) {
		fileRepository.save(is, filename, archive.parentId(), contentType);
	}
	

}
