package de.mq.archive.web;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import org.apache.wicket.model.IModel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.web.search.ArchiveModelParts;


public class EnumModelTest {

	
	private static final String CLAZZ_FIELD = "clazz";
	private static final String ID_FIELD = "id";
	private static final String TEXT = "For my name was Eliza Day ...";
	private static final String NAME = "Loveletter for Kylie";
	private static final String ID = "19680528";
	private static final Date DATE = new Date();
	private static final String ARCHIVE_ID = "4711";
	private static final String PERSON = "Kylie";
	private final TwoWayMapping<Archive, Enum<?>> enumModel = new BasicEnumModelImpl<Archive>(Arrays.asList(ArchiveModelParts.values()), ArchiveImpl.class);

	@Test
	public final void values() {
		Assert.assertEquals(ArchiveImpl.class, ReflectionTestUtils.getField(enumModel, CLAZZ_FIELD));

		
		final Map<Enum<?>, IModel<?>> models = models();
		models.keySet().forEach(key -> Arrays.asList(ArchiveModelParts.values()).contains(key));
		models.values().forEach(value -> Assert.assertTrue(value instanceof IModel));
		models.values().stream().map(value -> value.getObject()).forEach(mo -> Assert.assertNull(mo));
	}

	@SuppressWarnings("unchecked")
	private Map<Enum<?>, IModel<?>> models() {
		return (Map<Enum<?>, IModel<?>>) ReflectionTestUtils.getField(enumModel, "models");
	}
	
	@Test
	public final void toDomain() {
		Collection<String> persons = new HashSet<>();
		persons.add(PERSON);
		 enumModel.part(ArchiveModelParts.ArchiveId).setObject(ARCHIVE_ID);
		 enumModel.part(ArchiveModelParts.Category).setObject(Category.Correspondence);
		 enumModel.part(ArchiveModelParts.DocumentDate).setObject(DATE);
		 enumModel.part(ArchiveModelParts.Id).setObject(ID);
		 enumModel.part(ArchiveModelParts.Name).setObject(NAME);
		 enumModel.part(ArchiveModelParts.Text).setObject(TEXT);
		 enumModel.part(ArchiveModelParts.RelatedPersons).setObject( (Serializable) persons);
		
	
		final Archive archive = enumModel.toDomain();
		Assert.assertEquals(NAME, archive.name());
		Assert.assertEquals(ARCHIVE_ID, archive.archiveId());
		Assert.assertEquals(Category.Correspondence, archive.category());
		Assert.assertEquals(DATE, archive.documentDate());
		Assert.assertEquals(ID, archive.id());
		Assert.assertEquals(persons, new HashSet<>(archive.relatedPersons()));
		Assert.assertEquals(TEXT, archive.text());
	}
	
	@Test
	public final void intoWeb() {
		final Archive archive = new ArchiveImpl(NAME, Category.Correspondence, DATE, ARCHIVE_ID, TEXT);
		ReflectionTestUtils.setField(archive, ID_FIELD, ID);
		archive.assign(PERSON);
		
		enumModel.intoWeb(archive);
		
		Arrays.asList(ArchiveModelParts.values()).stream().filter(part -> part != ArchiveModelParts.RelatedPersons).forEach(part -> Assert.assertEquals(ReflectionTestUtils.invokeGetterMethod(archive,  StringUtils.uncapitalize(part.name())) , enumModel.part(part).getObject()));
	   Assert.assertEquals(new HashSet<>(archive.relatedPersons()), enumModel.part(ArchiveModelParts.RelatedPersons).getObject());
	}
	
	@Test
	public final void part() {
		@SuppressWarnings("unchecked")
		final IModel<String> model = Mockito.mock(IModel.class);
		models().put(ArchiveModelParts.ArchiveId, model);
		
		Assert.assertEquals(model, enumModel.part(ArchiveModelParts.ArchiveId));
	}

}
