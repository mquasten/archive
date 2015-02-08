package de.mq.archive.domain.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Profile;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;

@Named
@Profile("mock")
public class ArchiveRepositoryMock implements ArchiveRepository {

	static final ArchiveImpl ARCHIVE_02 = new ArchiveImpl("Gehaltsabrechnung Dezember", Category.SalaryPrintout, new GregorianCalendar(2014, 11, 1).getTime(), "4712");
	static final ArchiveImpl ARCHIVE_01 = new ArchiveImpl("Kontoauszug Dezember", Category.Statement, new GregorianCalendar(2014, 11, 1).getTime(), "4711");
	private final Map<String, Archive> archives = new HashMap<>();

	@PostConstruct
	void init() {
		final List<Archive> documents = new ArrayList<>();

		documents.add(ARCHIVE_01);
		documents.add(ARCHIVE_02);
		final Field field = ReflectionUtils.findField(ArchiveImpl.class, "id");
		field.setAccessible(true);
		documents.forEach(doc -> {
			ReflectionUtils.setField(field, doc, String.valueOf(UUID.nameUUIDFromBytes(doc.name().getBytes())));
			archives.put(doc.id(), doc);
		});
	}

	@Override
	public List<Archive> forCriterias(final Archive archive, final Paging paging) {
		final List<Archive> allResults = new ArrayList<>();
		archives.values().stream().filter(doc -> match(doc, archive)).forEach(doc -> allResults.add(doc));
		
		Collections.sort(allResults, (doc1, doc2) -> doc1.name().compareTo(doc2.name()));
		return Collections.unmodifiableList(new ArrayList<>(allResults.subList(paging.firstRow().intValue(), Math.min(allResults.size(), paging.firstRow().intValue() + paging.pageSize().intValue()))));

	}

	@Override
	public Number countForCriteria(final Archive archive) {
		return forCriterias(archive, new SimpleResultSetPagingImpl(Integer.MAX_VALUE, Integer.MAX_VALUE)).size();
	}

	private boolean match(final Archive doc, final Archive criteria) {
		if (!matchName(doc, criteria)) {
			return false;
		}
		if (!matchCategory(doc, criteria)) {
			return false;
		}
		if( !matchArchiveIds(doc, criteria) ) {
			return false;
		}
		return true;
	}

	private boolean matchArchiveIds(final Archive doc, final Archive criteria) {
		if (!StringUtils.hasText(criteria.archiveId())) {
			return true;
		}
		if (!StringUtils.startsWithIgnoreCase(doc.archiveId(), criteria.archiveId())) {
			return false;
		}

		return true;
	}

	private boolean matchCategory(final Archive doc, final Archive criteria) {
		if (criteria.category() == null) {
			return true;
		}

		if (doc.category() != criteria.category()) {
			return false;
		}
		return true;

	}

	private boolean matchName(final Archive doc, final Archive criteria) {
		if (!StringUtils.hasText(criteria.name())) {
			return true;
		}
		if (!StringUtils.startsWithIgnoreCase(doc.name(), criteria.name())) {
			return false;
		}

		return true;
	}

	@Override
	public void save(final Archive archive) {
		 final String id = String.valueOf(UUID.nameUUIDFromBytes(archive.name().getBytes()));
		 archives.put(id, archive);
	}

}
