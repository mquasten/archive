package de.mq.archive.domain.support;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Optional;

import de.mq.archive.domain.GridFsInfo;

public interface MongoFileRepository {

	void save(final InputStream is, final String name, final Optional<String> parentId,  final String contentType);
	
	Collection<GridFsInfo<String>> resources(final Optional<String> parentId);

	void deleteAll(Optional<String> parentId);

	void delete(String fileId);

	Entry<GridFsInfo<String>, byte[]> file(final String fileId);

	

}