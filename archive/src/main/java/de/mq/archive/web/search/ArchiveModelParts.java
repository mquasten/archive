package de.mq.archive.web.search;

import org.springframework.util.StringUtils;



public enum ArchiveModelParts implements WicketIdAware  {
		Id,

		Name,

		Category,

		Text,

		DocumentDate,

		RelatedPersons,

		ArchiveId;
		
		public final String wicketId() {
			return StringUtils.uncapitalize(name());
		}

}