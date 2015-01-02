package de.mq.archive.web.search;

import java.util.List;

import de.mq.archive.domain.Archive;

interface SearchPageController {

	List<Archive> archives(final Archive archive);

}