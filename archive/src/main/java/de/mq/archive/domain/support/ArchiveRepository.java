package de.mq.archive.domain.support;

import java.util.List;

import de.mq.archive.domain.Archive;

interface ArchiveRepository {

	List<Archive> forCriterias(final Archive archive, final Paging paging);
	
	Number countForCriteria(final Archive archive);
	
	void save(final Archive archive);

	Archive forId(final String id);

	void delete(Archive archive);

}