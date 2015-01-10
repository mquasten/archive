package de.mq.archive.web.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.EnumModel;

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
	private final SearchPageModel model = new SearchPageModelImpl(archiveWeb, listModel, selectedArchiveIdWeb, pageSizeWeb);
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
	public final void setArchives() {
		final List<Archive> archives = new ArrayList<>();
		archives.add(archive);
		model.setArchives(archives);

		Mockito.verify(listModel).setObject(archives);
	}

	@Test
	public final void getPageSize() {
		Mockito.when(pageSizeWeb.getObject()).thenReturn(PAGE_SIZE);
		Assert.assertEquals(PAGE_SIZE, model.getPageSize());
	}

	@Test
	public final void getSearchCriteriaWeb() {
		Assert.assertEquals(archiveWeb, model.getSearchCriteriaWeb());
	}

	@Test
	public final void getArchivesWeb() {
		Assert.assertEquals(listModel, model.getArchivesWeb());
	}

	@Test
	public final void getSelectedArchiveWeb() {
		Assert.assertEquals(selectedArchiveIdWeb, model.getSelectedArchiveWeb());
	}

	@Test
	public final void getPageSizeWeb() {
		Assert.assertEquals(pageSizeWeb, model.getPageSizeWeb());
	}

}
