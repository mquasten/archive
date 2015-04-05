package de.mq.archive.web.search;

import org.springframework.util.StringUtils;

public enum PagingWicketIds implements WicketIdAware {
	
	FirstPageButton,
	NextPageButton,
	PreviousPageButton,
	LastPageButton, PagingLabel;
	
	public final String wicketId() {
		return StringUtils.uncapitalize(name());
	}

}
