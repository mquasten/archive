package de.mq.archive.domain.support;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.util.Assert;

import com.mongodb.gridfs.GridFSFile;

import de.mq.archive.domain.GridFsInfo;

@Named
public class MongoFileRepositoryImpl implements MongoFileRepository {
	
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
		Assert.isTrue(parentId.isPresent(), "ParentId is mandatory, may be Parent isn't persistent");
		final Query query = new Query(Criteria.where(METADATA_FIELD).is(parentId).and(ALIASES_FIELD).is(name.toLowerCase().trim()));
		gridOperations.delete(query);
		
		final GridFSFile gridFSFile = gridOperations.store(is, name.trim(), contentType,  parentId);
		gridFSFile.put(ALIASES_FIELD, Stream.of(name.toLowerCase().trim()).toArray());
		
		gridFSFile.save();
		
	} 
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.domain.support.MongoFileRepository#resources(java.util.Optional)
	 */
	@Override
	public final Collection<GridFsInfo<String>> resources(final Optional<String> parentId) {
		final Query query = new Query(Criteria.where(METADATA_FIELD).is(parentId));
		final Collection<GridFsInfo<String>> results = new ArrayList<>();
		gridOperations.find(query).forEach( gridFSDBFile -> results.add( new GridFsInfo<String>(){

			@Override
			public Number contentLength() {
				return gridFSDBFile.getLength();
			}

			@Override
			public String filename() {
				return gridFSDBFile.getFilename();
			}

			@Override
			public Date lastModified() {
				return gridFSDBFile.getUploadDate();
			}

			@Override
			public String id() {
				if(   gridFSDBFile.getId() == null) {
					return null;
				}
				return gridFSDBFile.getId().toString();
			}

			@Override
			public String contentType() {
				return gridFSDBFile.getContentType();
			}}) );
		return results;
	}
	
	
	
	
	
	
}
