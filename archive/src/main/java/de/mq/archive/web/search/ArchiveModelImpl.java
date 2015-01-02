package de.mq.archive.web.search;

import java.util.Arrays;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.web.BasicEnumModelImpl;

	class ArchiveModelImpl extends BasicEnumModelImpl<Archive> implements ArchiveModel {

		public ArchiveModelImpl() {
			super(Arrays.asList(ArchiveModelParts.values()), ArchiveImpl.class);
			
		}

		private static final long serialVersionUID = 1L;
		

}
