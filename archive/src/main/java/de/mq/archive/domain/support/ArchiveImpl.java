package de.mq.archive.domain.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;

@Document(collection="Archive")
public class ArchiveImpl implements Archive {
	
	private static final long serialVersionUID = 1L;

	private final String name;
	
	private final Category category;
	
	private final String text; 
	
	private final Date documentDate;
	
	private Set<String> relatedPersons; 
	
	private String archiveId;
	
	@Id
	private String id;
	
	
	public ArchiveImpl(final String name, final Category category, final Date documentDate, final String archiveId) {
		this(name, category,documentDate,archiveId, null);
	}
	
	@SuppressWarnings("unused")
	private ArchiveImpl() {
		this(null,null,null,null);
	}
			
	
	public ArchiveImpl(final String name, final Category category, final Date documentDate, final String archiveId, final String text ) {
		this.name=name;
		this.category=category;
		this.documentDate=documentDate;
		this.archiveId=archiveId;
		this.text=text;
	}
	
	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#id()
	 */
	@Override
	public final String id() {
		return id;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#name()
	 */
	@Override
	public final String name() {
		return name;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#category()
	 */
	@Override
	public final Category category() {
		return category;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#text()
	 */
	@Override
	public final String text() {
		return text;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#documentDate()
	 */
	@Override
	public final Date documentDate() {
		return documentDate;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#relatedPersons()
	 */
	@Override
	public final List<String> relatedPersons() {
		final List<String> results = new ArrayList<>();
		if(relatedPersons == null){
			return Collections.unmodifiableList(results);
		}
		results.addAll(relatedPersons);
		Collections.sort( results, (p1, p2) ->  p2.compareToIgnoreCase(p2) );
		return Collections.unmodifiableList(results);
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#archiveId()
	 */
	@Override
	public final  String archiveId() {
		return archiveId;
	}
	
	@Override
	public final void assign(final String person){
		if(this.relatedPersons==null){
			relatedPersons=new HashSet<>();
		}
		relatedPersons.add(person);
	}
	
	@Override
	public final void remove(final String person){
		if(this.relatedPersons==null){
			return;
		}
		relatedPersons.remove(person);
	}


	public final Optional<String> parentId() {
		if( StringUtils.hasText(id)){
			return Optional.of(id);		
		}
		return Optional.empty();
	}


}
