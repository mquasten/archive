package de.mq.archive.domain.support;

import java.util.Collection;
import java.util.Date;







import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;

public class ArchiveTest {
	
	private static final String ID_FIELD = "id";
	private static final String RELATED_PERSONS_FIELD = "relatedPersons";
	private static final String PERSON_01 = "Kylie";
	private static final String PERSON_02 = "Danni";
	private static final String ID = "19680528";
	private static final String TEXT = "text";
	private static final String ARCHIVE_ID = "archiveId";
	private static final Date DOCUMENT_DATE = new Date();
	private static final Category CORRESPONDENCE = Category.Correspondence;
	private static final String ARCHIVE_NAME = "archiveName";
	

	private Set<String> persons = new HashSet<>();
	private final Archive archive = new ArchiveImpl(ARCHIVE_NAME, CORRESPONDENCE, DOCUMENT_DATE, ARCHIVE_ID,TEXT);
	
	@Before
	public final void setup() {
		persons.add(PERSON_01);
		persons.add(PERSON_02);
		ReflectionTestUtils.setField(archive, ID_FIELD, ID);
		ReflectionTestUtils.setField(archive, RELATED_PERSONS_FIELD, persons);
	}
	
	@Test
	public final void name() {
		Assert.assertEquals(ARCHIVE_NAME, archive.name());
	}
	
	@Test
	public final void  id() {
		Assert.assertEquals(ID, archive.id());
	}
	
	@Test
	public final void  category() {
		Assert.assertEquals(CORRESPONDENCE, archive.category());
	}
	
	@Test
	public final void text() {
		Assert.assertEquals(TEXT, archive.text());
	}
	
	@Test
	public final void documentDate() {
		Assert.assertEquals(DOCUMENT_DATE, archive.documentDate());
	}
	
	@Test
	public final void  archiveId() {
		Assert.assertEquals(ARCHIVE_ID, archive.archiveId());
	}
	
	@Test
	public final void  relatedPersons() {
		 final Collection<String> results = archive.relatedPersons();
		 Assert.assertEquals(2, results.size());
		 Assert.assertTrue(results.contains(PERSON_01));
		 Assert.assertTrue(results.contains(PERSON_02));
	}
	
	@Test
	public final void  relatedPersonsNull() {
		ReflectionTestUtils.setField(archive, RELATED_PERSONS_FIELD, null);
		Assert.assertNotNull(archive.relatedPersons());
		Assert.assertTrue(archive.relatedPersons().isEmpty());
	}
	
	@Test
	public final void create() {
		final Archive archive = BeanUtils.instantiateClass(ArchiveImpl.class);
		Assert.assertNull(archive.name());
		Assert.assertNull(archive.id());
		Assert.assertNull(archive.archiveId());
		Assert.assertNull(archive.text());
	}
	
	@Test
	public final void assign() {
		final Archive archive = BeanUtils.instantiateClass(ArchiveImpl.class);
		archive.assign(PERSON_01); 
		
		final Collection<String> results = personsField(archive);
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		archive.assign(PERSON_02);
		
		Assert.assertEquals(2, results.size());
		Assert.assertTrue(results.contains(PERSON_01));
		Assert.assertTrue(results.contains(PERSON_02));
		
	}

	@SuppressWarnings("unchecked")
	private Collection<String> personsField(final Archive archive) {
		return (Collection<String>) ReflectionTestUtils.getField(archive, RELATED_PERSONS_FIELD);
	}
	
	@Test
	public final void remove() {
		final Collection<String> results = personsField(archive);
		Assert.assertEquals(2, results.size());
		Assert.assertTrue(results.contains(PERSON_01));
		Assert.assertTrue(results.contains(PERSON_02));
		
		archive.remove(PERSON_02);
		Assert.assertEquals(1, results.size());
		Assert.assertTrue(results.contains(PERSON_01));
		Assert.assertFalse(results.contains(PERSON_02));
	}
	
	@Test
	public final void removeFromNullCollection() {
		final Archive archive = BeanUtils.instantiateClass(ArchiveImpl.class);
		archive.remove(PERSON_01);
	}

}
