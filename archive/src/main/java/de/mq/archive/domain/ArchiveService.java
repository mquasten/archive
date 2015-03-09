package de.mq.archive.domain;


import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import de.mq.archive.domain.support.ModifyablePaging;
import de.mq.archive.domain.support.Paging;

public interface ArchiveService {
	


	List<Archive> archives(final Archive archive, final Paging paging);

	ModifyablePaging paging(final Archive archive, final int pageSize);
	
	void save(final Archive archive);

	Archive archive(final String id);

	void upload(final Archive archive, final InputStream is, final String filename, final String contentType);

	Collection<GridFsInfo<String>> attachements(final Archive archive);

	void deleteAttachement(final String fileId); 

}