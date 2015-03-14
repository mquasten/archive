package de.mq.archive.domain.support;

import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.util.StringUtils;

import de.mq.archive.domain.Archive;

@Named
@Profile("db")
class ArchiveMongoRepositoryImpl implements ArchiveRepository {

	private static final String ARCHIVE_ID_FIELD = "archiveId";
	private static final String CATEGORY_FIELD = "category";
	private static final String NAME_FIELD = "name";
	private final MongoOperations mongoOperations;
	
	@Autowired
	ArchiveMongoRepositoryImpl(final MongoOperations mongoOperations){
		this.mongoOperations=mongoOperations;
	}
	

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.ArchiveRepository#forCriterias(de.mq.archive.domain.Archive, org.springframework.data.domain.Pageable)
	 */
	@Override
	public final List<Archive>forCriterias(final Archive archive, final Paging paging) {
		final Query query = query(archive);
		
		query.limit(paging.pageSize().intValue());
		query.with(new Sort(new Order(Direction.ASC, NAME_FIELD)));
		query.skip(paging.firstRow().intValue());
		return Collections.unmodifiableList(mongoOperations.find(query, Archive.class, ArchiveImpl.class.getAnnotation(Document.class).collection()));
	
	}
	
	public final Number countForCriteria(final Archive archive){
		return mongoOperations.count(query(archive), ArchiveImpl.class);
	}

	private Query query(final Archive archive) {
		final Query query = new Query();
		
		if( StringUtils.hasText(archive.name())){
			query.addCriteria(new Criteria(NAME_FIELD).regex(StringUtils.trimWhitespace(archive.name())));
		}
		
		if( archive.category() != null){
			query.addCriteria(new Criteria(CATEGORY_FIELD).is(archive.category()));
		}
		
		if( StringUtils.hasText(archive.archiveId())){
			query.addCriteria(new Criteria(ARCHIVE_ID_FIELD).regex(StringUtils.trimWhitespace(archive.archiveId())));
		}
		return query;
	}


	@Override
	public void save(final Archive archive) {
		mongoOperations.save(archive);
		
	}
	@Override
	public void delete(final Archive archive){
		mongoOperations.remove(forId(archive.id()));
	}
	
	@Override
	public final Archive forId(final String id){
		return mongoOperations.findById(id, ArchiveImpl.class);
	}
	
}
