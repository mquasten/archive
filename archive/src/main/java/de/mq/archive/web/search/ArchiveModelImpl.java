package de.mq.archive.web.search;

import java.util.Arrays;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.support.ArchiveImpl;

public class ArchiveModelImpl extends  BasicEnumModelImpl<Archive>  {

	enum Parts  {
		Id,

		Name,

		Category,

		Text,

		DocumentDate,

		RelatedPersons(),

		ArchiveId;

	}
	
	protected ArchiveModelImpl() {
		super(Arrays.asList(Parts.values()), ArchiveImpl.class);
		
	}

	private static final long serialVersionUID = 1L;

	
	

}
