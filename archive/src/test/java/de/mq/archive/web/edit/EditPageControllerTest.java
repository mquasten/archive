package de.mq.archive.web.edit;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.GridFsInfo;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.web.search.SearchPageModel;

public class EditPageControllerTest {

	private final ArchiveService archiveService = Mockito.mock(ArchiveService.class);

	private final EditPageController editPageController = new EditPageControllerImpl(archiveService);

	private final EditPageModel editPageModel = Mockito.mock(EditPageModel.class);

	private final Archive archive = Mockito.mock(Archive.class);

	private final SearchPageModel searchPageModel = Mockito.mock(SearchPageModel.class);
	private final Collection<GridFsInfo<String>> attachements = new ArrayList<>();

	@SuppressWarnings("unchecked")
	@Before
	public final void setup() {
		attachements.add(Mockito.mock(GridFsInfo.class));
		Mockito.when(archiveService.attachements(archive)).thenReturn(attachements);
	}

	@Test
	public final void save() {
		Mockito.when(editPageModel.getArchive()).thenReturn(archive);
		editPageController.save(editPageModel);
		Mockito.verify(archiveService).save(archive);
	}

	@Test
	public final void init() {
		Mockito.when(searchPageModel.getSelectedArchiveId()).thenReturn("19680528");
		Mockito.when(archiveService.archive("19680528")).thenReturn(archive);
		editPageController.init(searchPageModel, editPageModel);
		Mockito.verify(editPageModel, Mockito.times(1)).setArchive(archive);
		Mockito.verify(editPageModel).setEditable(true);
		attachements.forEach(attachement -> Mockito.verify(editPageModel).add(attachement));
	}

	@Test
	public final void initNew() {
		editPageController.init(editPageModel);
		ArgumentCaptor<Archive> archiveCaptor = ArgumentCaptor.forClass(Archive.class);
		Mockito.verify(editPageModel).setArchive(archiveCaptor.capture());
		Assert.assertEquals(ArchiveImpl.class, archiveCaptor.getValue().getClass());

	}

	@Test
	public final void delete() {
		Mockito.when(editPageModel.getArchive()).thenReturn(archive);
		Mockito.when(editPageModel.isPersistent()).thenReturn(true);
		editPageController.delete(editPageModel);
		Mockito.verify(archiveService).delete(archive);
	}

	@Test
	public final void deleteNotPersistent() {
		editPageController.delete(editPageModel);
		Mockito.verify(archiveService, Mockito.never()).delete(Mockito.any(Archive.class));
	}

	@Test
	public final void initReadOnly() {

		Mockito.when(searchPageModel.getSelectedArchiveId()).thenReturn("19680528");
		Mockito.when(archiveService.archive("19680528")).thenReturn(archive);

		editPageController.initReadOnly(searchPageModel, editPageModel);
		Mockito.verify(editPageModel).setArchive(archive);

		attachements.forEach(attachement -> Mockito.verify(editPageModel).add(attachement));
	}

}
