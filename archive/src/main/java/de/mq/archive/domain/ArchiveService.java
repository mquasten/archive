package de.mq.archive.domain;


import java.io.InputStream;
import java.util.List;

import de.mq.archive.domain.support.ModifyablePaging;
import de.mq.archive.domain.support.Paging;

public interface ArchiveService {
	


	List<Archive> archives(final Archive archive, final Paging paging);

	ModifyablePaging paging(final Archive archive, final int pageSize);
	
	void save(final Archive archive);

	Archive archive(final String id);

	void upload(Archive archive, InputStream is, String filename, String contentType); 

}