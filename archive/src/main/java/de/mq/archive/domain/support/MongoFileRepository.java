package de.mq.archive.domain.support;

import java.io.InputStream;

public interface MongoFileRepository {

	public abstract void save(InputStream is, String archiveId);

	public abstract byte[] read(String archiveId);

}