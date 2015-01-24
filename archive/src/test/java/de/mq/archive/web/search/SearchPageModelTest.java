package de.mq.archive.web.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.EnumModel;
import de.mq.archive.web.OneWayMapping;

public class SearchPageModelTest {

	private static final int PAGE_SIZE = 10;
	private static final String ID = "19680528";
	@SuppressWarnings("unchecked")
	private final EnumModel<Archive> archiveWeb = Mockito.mock(EnumModel.class);
	@SuppressWarnings("unchecked")
	private final IModel<List<Archive>> listModel = Mockito.mock(ListModel.class);
	@SuppressWarnings("unchecked")
	private final IModel<String> selectedArchiveIdWeb = Mockito.mock(IModel.class);
	@SuppressWarnings("unchecked")
	private final IModel<Number> pageSizeWeb = Mockito.mock(IModel.class);
	
	 @SuppressWarnings("unchecked")
	final OneWayMapping<Locale, Enum<?>>  labelsModel = Mockito.mock(OneWayMapping.class);
	
	private final SearchPageModel model = new SearchPageModelImpl(archiveWeb, labelsModel, listModel, selectedArchiveIdWeb, pageSizeWeb);
	private final SearchPageModelWeb modelWeb = (SearchPageModelWeb) model; 
	private final Archive archive = Mockito.mock(Archive.class);

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
		Mockito.when(listModel.getObject()).thenReturn(archives);
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
		Mockito.when(listModel.getObject()).thenReturn(archives);
		model.setArchives(archives);

		Mockito.verify(listModel).setObject(archives);
		Mockito.verify(selectedArchiveIdWeb, Mockito.times(0)).setObject(null);
		
		
	}
	
	@Test
	public final void setArchivesResetSelection() {
		final List<Archive> archives = new ArrayList<>();
		Mockito.when(archive.id()).thenReturn(ID);
		archives.add(archive);
		Mockito.when(selectedArchiveIdWeb.getObject()).thenReturn(UUID.randomUUID().toString());
		Mockito.when(listModel.getObject()).thenReturn(archives);
		model.setArchives(archives);
		
		Mockito.verify(listModel).setObject(archives);
		Mockito.verify(selectedArchiveIdWeb, Mockito.times(1)).setObject(null);
	}
	
	

	@Test
	public final void getPageSize() {
		Mockito.when(pageSizeWeb.getObject()).thenReturn(PAGE_SIZE);
		Assert.assertEquals(PAGE_SIZE, model.getPageSize());
	}

	@Test
	public final void getSearchCriteriaWeb() {
		Assert.assertEquals(archiveWeb, modelWeb.getSearchCriteriaWeb());
	}

	@Test
	public final void getArchivesWeb() {
		Assert.assertEquals(listModel, modelWeb.getArchivesWeb());
	}

	@Test
	public final void getSelectedArchiveWeb() {
		Assert.assertEquals(selectedArchiveIdWeb, modelWeb.getSelectedArchiveWeb());
	}

	@Test
	public final void getPageSizeWeb() {
		Assert.assertEquals(pageSizeWeb, modelWeb.getPageSizeWeb());
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

}
