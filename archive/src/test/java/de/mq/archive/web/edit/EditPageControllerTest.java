package de.mq.archive.web.edit;



import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.web.search.SearchPageModel;

public class EditPageControllerTest {
	
	private final ArchiveService archiveService = Mockito.mock(ArchiveService.class);
	
	private final EditPageController editPageController = new EditPageControllerImpl(archiveService);
	
	private final EditPageModel editPageModel = Mockito.mock(EditPageModel.class);
	
	private final Archive archive = Mockito.mock(Archive.class);
	
	private final SearchPageModel searchPageModel = Mockito.mock(SearchPageModel.class);
	
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
	}
	
	@Test
	public final void initNew() {
		editPageController.init(editPageModel);
		ArgumentCaptor<Archive> archiveCaptor = ArgumentCaptor.forClass(Archive.class);
		Mockito.verify(editPageModel).setArchive(archiveCaptor.capture());
		Assert.assertEquals(ArchiveImpl.class, archiveCaptor.getValue().getClass());
	}

}
