package de.mq.archive.domain.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResourceAccessException;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import de.mq.archive.domain.GridFsInfo;

@Named
public class MongoFileRepositoryImpl implements MongoFileRepository {
	
	private static final String ID_FIELD = "_id";
	private static final String ALIASES_FIELD = "aliases";
	private static final String METADATA_FIELD = "metadata";
	private final GridFsOperations gridOperations;

	@Autowired
	public MongoFileRepositoryImpl(final GridFsOperations gridOperations) {
		this.gridOperations = gridOperations;
	}
	

	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.domain.support.MongoFileRepository#save(java.io.InputStream, java.lang.String, java.util.Optional, java.lang.String)
	 */
	@Override
	public final void save(final InputStream is, final String name, final Optional<String> parentId, final String contentType) {
		parentExistsGuard(parentId);
		final Query query = new Query(Criteria.where(METADATA_FIELD).is(parentId).and(ALIASES_FIELD).is(name.toLowerCase().trim()));
		gridOperations.delete(query);
		
		final GridFSFile gridFSFile = gridOperations.store(is, name.trim(), contentType,  parentId);
		gridFSFile.put(ALIASES_FIELD, Stream.of(name.toLowerCase().trim()).toArray());
		
		gridFSFile.save();
		
	}


	private void parentExistsGuard(final Optional<String> parentId) {
		Assert.isTrue(parentId.isPresent(), "ParentId is mandatory, may be Parent isn't persistent");
	} 
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.domain.support.MongoFileRepository#resources(java.util.Optional)
	 */
	@Override
	public final Collection<GridFsInfo<String>> resources(final Optional<String> parentId) {
		final Query query = new Query(Criteria.where(METADATA_FIELD).is(parentId));
		final Collection<GridFsInfo<String>> results = new ArrayList<>();
		gridOperations.find(query).forEach( gridFSDBFile -> results.add( gridFsInfo(gridFSDBFile))); 
		return results;
	}


	private GridFsInfo<String> gridFsInfo(final GridFSDBFile gridFSDBFile) {
		return new GridFsInfoImpl( gridFSDBFile.getId() != null ? gridFSDBFile.getId().toString() : null , gridFSDBFile.getFilename(), gridFSDBFile.getLength(),gridFSDBFile.getUploadDate(), gridFSDBFile.getContentType());
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.domain.support.MongoFileRepository#deleteAll(java.util.Optional)
	 */
	@Override
	public final void deleteAll(final Optional<String> parentId) {
		parentExistsGuard(parentId);
		gridOperations.delete(new Query(Criteria.where(METADATA_FIELD).is(parentId)));
	}
	

	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.domain.support.MongoFileRepository#delete(java.lang.String)
	 */
	@Override
	public final void delete(final String fileId) {
		gridOperations.delete(new Query(Criteria.where(ID_FIELD).is(fileId)));
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.domain.support.MongoFileRepository#file(java.lang.String)
	 */
	@Override
	public final Entry<GridFsInfo<String>, byte[]> file(final String fileId) {
		final GridFSDBFile result = gridOperations.findOne(new Query(Criteria.where(ID_FIELD).is(fileId)));
		
		try(final InputStream is = result.getInputStream()) {
			return new AbstractMap.SimpleEntry<GridFsInfo<String>,byte[]>(gridFsInfo(result), FileCopyUtils.copyToByteArray(is));
		} catch (final IOException ex) {
			throw new ResourceAccessException("Error reding Stream from Mongo", ex);
		}
	}
	
	
}
