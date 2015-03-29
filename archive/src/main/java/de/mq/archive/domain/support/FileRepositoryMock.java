package de.mq.archive.domain.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.inject.Named;

import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResourceAccessException;

import de.mq.archive.domain.GridFsInfo;

@Named
@Profile("mock")
public class FileRepositoryMock implements MongoFileRepository {

	private final Map<String,Map<String,GridFsInfo<String>>> documents = new HashMap<>();
	
	private final Map<String, byte[]> files = new HashMap<>();
	
	@Override
	public void save(final InputStream is, final String name, final Optional<String> parentId, final String contentType) {
		Assert.hasText(name, "Filename is mandatory");
		parentIdExistsGuard(parentId);
		if( ! documents.containsKey(parentId.get()) ) {
			documents.put(parentId.get(), new HashMap<>());
		}
		final String id = new UUID(parentId.hashCode(), name.toLowerCase().hashCode()).toString();
		byte[] content = content(is);
		
		documents.get(parentId.get()).put(name.toLowerCase(), new GridFsInfoImpl(id, name, content.length, new Date(), contentType));
		
		files.put(id, content);
	}

	private void parentIdExistsGuard(final Optional<String> parentId) {
		Assert.isTrue(parentId.isPresent() , "ParentId is mandatory");
	}

	private byte[] content(final InputStream is) {
		try {
			return  FileCopyUtils.copyToByteArray(is);
		} catch (final IOException ex) {
			throw new ResourceAccessException("Unable to process File" , ex);
		}
	}

	@Override
	public Collection<GridFsInfo<String>> resources(final Optional<String> parentId) {
		parentIdExistsGuard(parentId);
		final Collection<GridFsInfo<String>> results = new ArrayList<>();
		if( ! documents.containsKey(parentId.get()) ) {
			return Collections.unmodifiableCollection(results);
		}
		
		results.addAll(documents.get(parentId.get()).values());
		return  Collections.unmodifiableCollection(results);
	}

	@Override
	public void deleteAll(final Optional<String> parentId) {
		parentIdExistsGuard(parentId);
		if( ! documents.containsKey(parentId.get())){
			return;
		}
		
		documents.get(parentId.get()).values().stream().map(info -> info.id()).forEach(id -> files.remove(id));
		documents.remove(parentId);
	}

	@Override
	public void delete(final String fileId) {
		 final Optional<Entry<String,GridFsInfo<String>>> entry = findByFileId(fileId);
		 if( ! entry.isPresent()) {
			 return;
		 }
		 
		 final Map<String,GridFsInfo<String>> infos = documents.get(entry.get().getKey());
		 if( infos == null){
			return;
		 }
		 infos.remove(entry.get().getValue().filename().toLowerCase());
		 files.remove(entry.get().getValue().id());
		
	}

	private Optional<Entry<String,GridFsInfo<String>>> findByFileId(final String fileId) {
		Assert.hasText(fileId, "FileId is mandatory");	
		final Set<Entry<String, GridFsInfo<String>>> infos = new  HashSet<>();
		documents.entrySet().forEach(e ->  e.getValue().values().forEach(info -> infos.add(new AbstractMap.SimpleEntry<>(e.getKey(), info))));
		return  infos.stream().filter(entry -> entry.getValue().id().equals(fileId)).findFirst();
		
	}

	@Override
	public Entry<GridFsInfo<String>, byte[]> file(final String fileId) {
	
		final Optional<Entry<String,GridFsInfo<String>>> entry = findByFileId(fileId);
		Assert.isTrue(entry.isPresent(), "Entry not found for file : " + fileId);
		
		final byte[] content =  files.get(fileId);
		Assert.notNull(content, "Content not found for : " + fileId);
		
		return new AbstractMap.SimpleEntry<>(entry.get().getValue(), content);
	}

}
