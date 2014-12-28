package de.mq.archive.domain.support;

import java.util.Date;
import java.util.Set;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;

public class ArchiveImpl implements Archive {
	
	private static final long serialVersionUID = 1L;

	private final String name;
	
	private final Category category;
	
	private final String text; 
	
	private final Date documentDate;
	
	private Set<String> relatedPersons; 
	
	private String id;
	
	public ArchiveImpl(final String name, final Category category, final Date documentDate) {
		this(name, category,documentDate, null);
	}
	
	public ArchiveImpl(final String name, final Category category, final Date documentDate, final String text ) {
		this.name=name;
		this.category=category;
		this.documentDate=documentDate;
		this.text=text;
	}
	
	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#id()
	 */
	@Override
	public String id() {
		return id;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#name()
	 */
	@Override
	public String name() {
		return name;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#category()
	 */
	@Override
	public Category category() {
		return category;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#text()
	 */
	@Override
	public String text() {
		return text;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#documentDate()
	 */
	@Override
	public Date documentDate() {
		return documentDate;
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.domain.support.Archive#relatedPersons()
	 */
	@Override
	public Set<String> relatedPersons() {
		return relatedPersons;
	}



}
