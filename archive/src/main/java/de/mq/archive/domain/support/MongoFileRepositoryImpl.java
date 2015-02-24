package de.mq.archive.domain.support;

import java.io.InputStream;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.util.FileCopyUtils;

@Named
public class MongoFileRepositoryImpl implements MongoFileRepository {
	
	private final GridFsOperations gridOperations;

	@Autowired
	public MongoFileRepositoryImpl(GridFsOperations gridOperations) {
		this.gridOperations = gridOperations;
	} 
	
	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.MongoFileRepository#save(java.io.InputStream, java.lang.String)
	 */
	@Override
	public final void save(final InputStream is, final String archiveId) {
		gridOperations.store(is, archiveId);
	}
	
	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.MongoFileRepository#read(java.lang.String)
	 */
	@Override
	public final byte[] read(final String archiveId) {
		final GridFsResource result = gridOperations.getResource(archiveId);
		if( result == null){
			return null;
		}
		try {
			return FileCopyUtils.copyToByteArray(result.getInputStream());
		} catch (final Exception ex) {
			  throw new IllegalStateException(ex);
		}
	}

}
