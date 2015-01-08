package de.mq.archive.domain.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;

public class ArchiveRepositoryTest {

	private static final Long COUNTER = Long.valueOf(42);
	private static final String ARCHIVE_ID_FIELDS = "archiveId";
	private static final String CRITERIA_FIELD = "criteria";
	private static final String CATEGORY_FIELD = "category";
	private static final String NAME_FIELD = "name";
	private final MongoOperations mongoOperations = Mockito.mock(MongoOperations.class);
	private final ArchiveRepository archiveRepository = new ArchiveMongoRepositoryImpl(mongoOperations);
	private Archive archive = Mockito.mock(Archive.class);
	private Paging paging = Mockito.mock(Paging.class);
	private Collection<Archive> archives = new ArrayList<>();

	final ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
	@SuppressWarnings("rawtypes")
	final ArgumentCaptor<Class> classCaptor = ArgumentCaptor.forClass(Class.class);
	final ArgumentCaptor<String> documentCaptor = ArgumentCaptor.forClass(String.class);

	@Before
	public final void setup() {
		Mockito.when(paging.pageSize()).thenReturn(10);
		Mockito.when(paging.firstRow()).thenReturn(0);

		Mockito.when(archive.name()).thenReturn("Loveletter for Kylie");
		Mockito.when(archive.archiveId()).thenReturn("19680528");
		Mockito.when(archive.category()).thenReturn(Category.Correspondence);
		archives.add(archive);

	}

	@SuppressWarnings("unchecked")
	@Test
	public final void forCriterias() {
		Mockito.when(mongoOperations.find(queryCaptor.capture(), classCaptor.capture(), documentCaptor.capture())).thenReturn(archives);
		Assert.assertEquals(archives, archiveRepository.forCriterias(archive, paging));
		Assert.assertEquals(Archive.class, classCaptor.getValue());
		Assert.assertEquals(ArchiveImpl.class.getAnnotation(Document.class).collection(), documentCaptor.getValue());

		final Map<String, Criteria> results = (Map<String, Criteria>) ReflectionTestUtils.getField(queryCaptor.getValue(), CRITERIA_FIELD);

		Assert.assertEquals(3, results.size());
		Assert.assertTrue(results.containsKey(NAME_FIELD));
		Assert.assertTrue(results.containsKey(CATEGORY_FIELD));
		Assert.assertTrue(results.containsKey(ARCHIVE_ID_FIELDS));
		Assert.assertEquals(archive.name(), ((Pattern) results.get(NAME_FIELD).getCriteriaObject().get(NAME_FIELD)).pattern());
		Assert.assertEquals(archive.category(), ((Category) results.get(CATEGORY_FIELD).getCriteriaObject().get(CATEGORY_FIELD)));
		Assert.assertEquals(archive.archiveId(), ((Pattern) results.get(ARCHIVE_ID_FIELDS).getCriteriaObject().get(ARCHIVE_ID_FIELDS)).pattern());

		Mockito.verify(paging, Mockito.times(1)).firstRow();
		Mockito.verify(paging, Mockito.times(1)).pageSize();
		final Map<String, Number> sortMap = queryCaptor.getValue().getSortObject().toMap();
		Assert.assertEquals(1, sortMap.size());
		Assert.assertEquals(NAME_FIELD, sortMap.keySet().iterator().next());
		Assert.assertEquals(1, sortMap.values().iterator().next());
		Assert.assertEquals(paging.firstRow(), queryCaptor.getValue().getSkip());
		Assert.assertEquals(paging.pageSize(), queryCaptor.getValue().getLimit());
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void forCriteriasWithoutSearchFields() {
		Mockito.when(archive.name()).thenReturn(null);
		Mockito.when(archive.archiveId()).thenReturn(null);
		Mockito.when(archive.category()).thenReturn(null);
		Mockito.when(mongoOperations.find(queryCaptor.capture(), classCaptor.capture(), documentCaptor.capture())).thenReturn(archives);
		Assert.assertEquals(archives, archiveRepository.forCriterias(archive, paging));
		Assert.assertEquals(Archive.class, classCaptor.getValue());
		Assert.assertEquals(ArchiveImpl.class.getAnnotation(Document.class).collection(), documentCaptor.getValue());

		final Map<String, Criteria> results = (Map<String, Criteria>) ReflectionTestUtils.getField(queryCaptor.getValue(), CRITERIA_FIELD);

		Assert.assertTrue(results.isEmpty());
	}

	@Test
	public final void countForCriteria() {
		Mockito.when(mongoOperations.count(queryCaptor.capture(), classCaptor.capture())).thenReturn(COUNTER);

		Assert.assertEquals(COUNTER, archiveRepository.countForCriteria(archive));
		@SuppressWarnings("unchecked")
		final Map<String, Criteria> results = (Map<String, Criteria>) ReflectionTestUtils.getField(queryCaptor.getValue(), CRITERIA_FIELD);
		Assert.assertEquals(3, results.size());
		Assert.assertTrue(results.containsKey(NAME_FIELD));
		Assert.assertTrue(results.containsKey(CATEGORY_FIELD));
		Assert.assertTrue(results.containsKey(ARCHIVE_ID_FIELDS));
		Assert.assertEquals(archive.name(), ((Pattern) results.get(NAME_FIELD).getCriteriaObject().get(NAME_FIELD)).pattern());
		Assert.assertEquals(archive.category(), ((Category) results.get(CATEGORY_FIELD).getCriteriaObject().get(CATEGORY_FIELD)));
		Assert.assertEquals(archive.archiveId(), ((Pattern) results.get(ARCHIVE_ID_FIELDS).getCriteriaObject().get(ARCHIVE_ID_FIELDS)).pattern());

		Assert.assertNull(queryCaptor.getValue().getSortObject());

		Assert.assertEquals(0, queryCaptor.getValue().getSkip());
		Assert.assertEquals(0, queryCaptor.getValue().getLimit());

	}

}
