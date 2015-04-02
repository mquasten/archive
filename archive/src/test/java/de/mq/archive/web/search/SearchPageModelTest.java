package de.mq.archive.web.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.wicket.model.IModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.domain.support.ModifyablePaging;
import de.mq.archive.domain.support.Paging;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;

public class SearchPageModelTest {

	private static final String PAGING_FIELD = "paging";
	private static final Date DATE = new GregorianCalendar(1968, 4, 28).getTime();
	private static final String NAME = "loveLetter for kylie";
	private static final int PAGE_SIZE = 10;
	private static final String ID = "19680528";
	@SuppressWarnings("unchecked")
	private TwoWayMapping<Archive, Enum<?>> archiveWeb = Mockito.mock(TwoWayMapping.class);
	
	@SuppressWarnings("unchecked")
	private final IModel<String> selectedArchiveIdWeb = Mockito.mock(IModel.class);


	
	 @SuppressWarnings("unchecked")
	final OneWayMapping<Locale, Enum<?>>  labelsModel = Mockito.mock(OneWayMapping.class);
	
	private final SearchPageModel model = new SearchPageModelImpl(archiveWeb, labelsModel,  PAGE_SIZE);
	private final SearchPageModelWeb modelWeb = (SearchPageModelWeb) model; 
	private final Archive archive = Mockito.mock(Archive.class);
	
	@Before
	public final void setup() {
		ReflectionTestUtils.setField(model, "selectedArchive", selectedArchiveIdWeb);
		
	}

	@Test
	public final void getSearchCriteria() {
		Mockito.when(archiveWeb.toDomain()).thenReturn(archive);
		Assert.assertEquals(archive, model.getSearchCriteria());
	}

	@Test
	public final void getSelectedArchiveId() {
		Mockito.when(selectedArchiveIdWeb.getObject()).thenReturn(ID);
		Assert.assertEquals(ID, model.getSelectedArchiveId());

	}
	@Test
	public final void getSelectedArchiveIdDefaultValue() {
		Assert.assertFalse(StringUtils.hasText(model.getSelectedArchiveId()));
	}
	@Test
	public final void getArchives() {
		final List<Archive> archives = new ArrayList<>();
		Mockito.when(archive.id()).thenReturn(ID);
		archives.add(archive);
		model.setArchives(archives);
		
		final List<Archive> results = ((SearchPageModelImpl)model).getArchives();
		Assert.assertEquals(archives, results);
	}
	
	@Test
	public final void getArchivesDefault() {
		final List<Archive> results = ((SearchPageModelImpl)model).getArchives();
		Assert.assertNotNull(results);
		Assert.assertTrue(results.isEmpty());
	}

	@Test
	public final void setArchivesKeepSelection() {
		final List<Archive> archives = new ArrayList<>();
		Mockito.when(archive.id()).thenReturn(ID);
		archives.add(archive);
		Mockito.when(selectedArchiveIdWeb.getObject()).thenReturn(ID);
	
		model.setArchives(archives);

		Mockito.verify(selectedArchiveIdWeb, Mockito.times(0)).setObject(null);
		
		
	}
	
	@Test
	public final void setArchivesResetSelection() {
		final List<Archive> archives = new ArrayList<>();
		Mockito.when(archive.id()).thenReturn(ID);
		archives.add(archive);
		Mockito.when(selectedArchiveIdWeb.getObject()).thenReturn(UUID.randomUUID().toString());
	
		model.setArchives(archives);
		
		Mockito.verify(selectedArchiveIdWeb, Mockito.times(1)).setObject(null);
	}
	
	

	@Test
	public final void getPageSize() {
		
		Assert.assertEquals(PAGE_SIZE, model.getPageSize());
	}

	@Test
	public final void getSearchCriteriaWeb() {
		Assert.assertEquals(archiveWeb, modelWeb.getSearchCriteriaWeb());
	}

	

	@Test
	public final void getSelectedArchiveWeb() {
		Assert.assertEquals(selectedArchiveIdWeb, modelWeb.getSelectedArchiveWeb());
	}

	
	
	@Test
	public final void  isSeelected() {
		Mockito.when(selectedArchiveIdWeb.getObject()).thenReturn(ID);
		Assert.assertTrue(modelWeb.isSelected());
		
		Mockito.when(selectedArchiveIdWeb.getObject()).thenReturn(null);
		Assert.assertFalse(modelWeb.isSelected());
	}
	
	@Test
	public final void getI18NLabels() {
		Assert.assertEquals(labelsModel, modelWeb.getI18NLabels());
	}
	
	@Test
	public final void  getArchivesWeb() {
		final List<Archive> archives = new ArrayList<>();
		archives.add(new ArchiveImpl(NAME, Category.Correspondence, DATE, ID));
		ReflectionTestUtils.setField(model, "archives", archives);
		final List<TwoWayMapping<Archive, Enum<?>>> results =  modelWeb.getArchivesWeb2();
		Assert.assertEquals(1, results.size());
		final TwoWayMapping<Archive, Enum<?>> result = results.iterator().next();
		Assert.assertEquals(ID,result.part(ArchiveModelParts.ArchiveId).getObject());
		Assert.assertEquals(NAME,result.part(ArchiveModelParts.Name).getObject());
		Assert.assertEquals(Category.Correspondence,result.part(ArchiveModelParts.Category).getObject());
		Assert.assertEquals(DATE,result.part(ArchiveModelParts.DocumentDate).getObject());
	}
	@Test
	public final void getPagingInfo() {
		final Paging paging = Mockito.mock(ModifyablePaging.class);
		Mockito.when(paging.currentPage()).thenReturn(1);
		Mockito.when(paging.maxPages()).thenReturn(42);
		ReflectionTestUtils.setField(model, PAGING_FIELD, paging);
		Assert.assertEquals(String.format("%s/%s", paging.currentPage(), paging.maxPages()), (((SearchPageModelWeb)model).getPagingInfo().getObject()));
	}
	
	@Test
	public final void getPagingInfoNoPaging() {
		Assert.assertTrue(((SearchPageModelWeb)model).getPagingInfo().getObject().isEmpty());
	}
	
	@Test
	public final void getPaging() {
		final Paging paging = Mockito.mock(ModifyablePaging.class);
		ReflectionTestUtils.setField(model, PAGING_FIELD, paging);
		Assert.assertTrue( model.getPaging().isPresent());
		Assert.assertEquals(paging, model.getPaging().get());
		
	}
	
	@Test
	public final void getPagingNoPaging() {
		Assert.assertFalse(model.getPaging().isPresent());
	}
	
	@Test
	public final void setPaging() {
		final ModifyablePaging paging = Mockito.mock(ModifyablePaging.class);
		model.setPaging(paging);
		Assert.assertEquals(paging, ReflectionTestUtils.getField(model, PAGING_FIELD));
	}
	
	@Test
	public final void hasPaging() {
		Assert.assertFalse(((SearchPageModelWeb)model).hasPaging());
		final Paging paging = Mockito.mock(ModifyablePaging.class);
		ReflectionTestUtils.setField(model, PAGING_FIELD, paging);
		Assert.assertTrue(((SearchPageModelWeb)model).hasPaging());
	}
	
	@Test
	public final void isNotFirstPage() {
		
		final Paging paging = Mockito.mock(ModifyablePaging.class);
		Mockito.when(paging.isBegin()).thenReturn(true);
		Assert.assertTrue((((SearchPageModelWeb)model).isNotFirstPage()));
		ReflectionTestUtils.setField(model, PAGING_FIELD, paging);
		Assert.assertFalse((((SearchPageModelWeb)model).isNotFirstPage()));
		
		
	}
	
	@Test
	public final void  hasNextPage() {
		
		final Paging paging = Mockito.mock(ModifyablePaging.class);
		Mockito.when(paging.hasNextPage()).thenReturn(true);
		Assert.assertFalse((((SearchPageModelWeb)model).hasNextPage()));
		ReflectionTestUtils.setField(model, PAGING_FIELD, paging);
		Assert.assertTrue((((SearchPageModelWeb)model).hasNextPage()));
	}
	
	@Test
	public final void hasPriviousPage() {
		
		final Paging paging = Mockito.mock(ModifyablePaging.class);
		Mockito.when(paging.hasPreviousPage()).thenReturn(true);
		Assert.assertFalse(((((SearchPageModelWeb)model).hasPriviousPage())));
		ReflectionTestUtils.setField(model, PAGING_FIELD, paging);
		Assert.assertTrue(((((SearchPageModelWeb)model).hasPriviousPage())));
	}
	
	@Test
	public final void isNotLastPage() {
		Assert.assertFalse(((SearchPageModelWeb)model).hasPaging());
		final Paging paging = Mockito.mock(ModifyablePaging.class);
		Assert.assertTrue((((SearchPageModelWeb)model).isNotLastPage()));
		Mockito.when(paging.isEnd()).thenReturn(true);
		ReflectionTestUtils.setField(model, PAGING_FIELD, paging);
		Assert.assertFalse((((SearchPageModelWeb)model).isNotLastPage()));
		
		
	}


}
