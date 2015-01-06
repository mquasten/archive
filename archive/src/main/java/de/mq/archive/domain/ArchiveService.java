package de.mq.archive.domain;


import java.util.List;

import de.mq.archive.domain.support.ModifyablePaging;
import de.mq.archive.domain.support.Paging;

public interface ArchiveService {
	


	List<Archive> archives(final Archive archive, final Paging paging);

	ModifyablePaging paging(final Archive archive, final int pageSize);

}