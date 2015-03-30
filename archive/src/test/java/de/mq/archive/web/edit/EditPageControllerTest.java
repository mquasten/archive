package de.mq.archive.web.edit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.web.client.ResourceAccessException;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.GridFsInfo;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.domain.support.ModifyablePaging;
import de.mq.archive.web.search.SearchPageModel;

public class EditPageControllerTest {

	private static final byte[] CONTENT = "Hello, my lovely wild rose ...".getBytes();

	private static final String CONTENT_TYPE = "application/pdf";

	private static final String FILE_NAME = "LoveLeterForKylie";

	private static final String ID = "19680528";

	private final ArchiveService archiveService = Mockito.mock(ArchiveService.class);

	private final EditPageController editPageController = new EditPageControllerImpl(archiveService);

	private final EditPageModel editPageModel = Mockito.mock(EditPageModel.class);

	private final Archive archive = Mockito.mock(Archive.class);

	private final SearchPageModel searchPageModel = Mockito.mock(SearchPageModel.class);
	private final Collection<GridFsInfo<String>> attachements = new ArrayList<>();

	private final FileUploadField fileUploadField = Mockito.mock(FileUploadField.class);
	final RequestCycle requestCycle = Mockito.mock(RequestCycle.class);

	@SuppressWarnings("unchecked")
	@Before
	public final void setup() {
		attachements.add(Mockito.mock(GridFsInfo.class));
		Mockito.when(archiveService.attachements(archive)).thenReturn(attachements);
	}

	@Test
	public final void save() {
		Mockito.when(editPageModel.getArchive()).thenReturn(archive);
		editPageController.save(editPageModel, searchPageModel);
		Mockito.verify(archiveService).save(archive);
		Mockito.verify(searchPageModel).setPaging(null);
	}

	@Test
	public final void init() {
		Mockito.when(searchPageModel.getSelectedArchiveId()).thenReturn(ID);
		Mockito.when(archiveService.archive(ID)).thenReturn(archive);
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
		editPageController.delete(editPageModel, searchPageModel);
		Mockito.verify(archiveService).delete(archive);
		Mockito.verify(searchPageModel).setPaging(Mockito.any(ModifyablePaging.class));
	}

	@Test
	public final void deleteNotPersistent() {
		editPageController.delete(editPageModel, searchPageModel);
		Mockito.verify(archiveService, Mockito.never()).delete(Mockito.any(Archive.class));
		Mockito.verify(searchPageModel, Mockito.never()).setPaging(Mockito.any(ModifyablePaging.class));
	}

	@Test
	public final void initReadOnly() {

		Mockito.when(searchPageModel.getSelectedArchiveId()).thenReturn(ID);
		Mockito.when(archiveService.archive(ID)).thenReturn(archive);

		editPageController.initReadOnly(searchPageModel, editPageModel);
		Mockito.verify(editPageModel).setArchive(archive);

		attachements.forEach(attachement -> Mockito.verify(editPageModel).add(attachement));
	}

	@Test
	public final void upload() throws IOException {

		final FileUpload fileUpload = Mockito.mock(FileUpload.class);
		Mockito.when(fileUploadField.getFileUpload()).thenReturn(fileUpload);

		final InputStream is = Mockito.mock(InputStream.class);
		Mockito.when(editPageModel.getArchive()).thenReturn(archive);
		Mockito.when(fileUpload.getInputStream()).thenReturn(is);
		Mockito.when(fileUpload.getClientFileName()).thenReturn(FILE_NAME);
		Mockito.when(fileUpload.getContentType()).thenReturn(CONTENT_TYPE);
		editPageController.uplaod(editPageModel, fileUploadField);

		Mockito.verify(archiveService).upload(editPageModel.getArchive(), is, FILE_NAME, CONTENT_TYPE);
	}

	@Test
	public final void uploadNoFile() {

		editPageController.uplaod(editPageModel, fileUploadField);
		Mockito.verify(archiveService, Mockito.never()).upload(Mockito.any(Archive.class), Mockito.any(InputStream.class), Mockito.anyString(), Mockito.anyString());
	}

	@Test(expected = ResourceAccessException.class)
	public final void uploadSucks() throws IOException {
		final FileUpload fileUpload = Mockito.mock(FileUpload.class);
		Mockito.when(fileUploadField.getFileUpload()).thenReturn(fileUpload);
		Mockito.when(fileUpload.getInputStream()).thenThrow(new IOException("Don't worry only for test"));

		editPageController.uplaod(editPageModel, fileUploadField);
	}

	@Test
	public final void deleteUpload() {
		Mockito.when(editPageModel.getSelectedAttachementId()).thenReturn(ID);
		Mockito.when(editPageModel.getArchive()).thenReturn(archive);
		Mockito.when(archiveService.attachements(archive)).thenReturn(attachements);

		editPageController.deleteUpload(editPageModel);

		Mockito.verify(archiveService).deleteAttachement(ID);
		Mockito.verify(editPageModel).setArchive(archive);
		attachements.forEach(attachement -> Mockito.verify(editPageModel).add(attachement));
	}

	@Test
	public final void deleteUploadNothingSelected() {
		editPageController.deleteUpload(editPageModel);

		Mockito.verify(archiveService, Mockito.never()).deleteAttachement(Mockito.anyString());
	}

	@Test
	public final void showAttachement() throws IOException {
		
		Mockito.when(editPageModel.getSelectedAttachementId()).thenReturn(ID);
		@SuppressWarnings("unchecked")
		final Entry<GridFsInfo<String>, byte[]> entry = Mockito.mock(Entry.class);
		@SuppressWarnings("unchecked")
		final GridFsInfo<String> gridFsInfo = Mockito.mock(GridFsInfo.class);
		Mockito.when(entry.getKey()).thenReturn(gridFsInfo);
		Mockito.when(entry.getValue()).thenReturn(CONTENT);
		Mockito.when(archiveService.content(ID)).thenReturn(entry);
		editPageController.showAttachement(editPageModel, requestCycle);
		final ArgumentCaptor<IRequestHandler> requestHandler = ArgumentCaptor.forClass(IRequestHandler.class);
		Mockito.verify(requestCycle).scheduleRequestHandlerAfterCurrent(requestHandler.capture());

		final ResourceStreamRequestHandler handler = (ResourceStreamRequestHandler) requestHandler.getValue();
		final AbstractResourceStreamWriter streamWriter = (AbstractResourceStreamWriter) handler.getResourceStream();
		final OutputStream os = Mockito.mock(OutputStream.class);
		streamWriter.write(os);
		final ArgumentCaptor<byte[]> contentCaptor = ArgumentCaptor.forClass(byte[].class);
		Mockito.verify(os).write(contentCaptor.capture());
		Assert.assertEquals(CONTENT, contentCaptor.getValue());

	}
	
	@Test
	public final void showAttachementNothingSelected() {
		editPageController.showAttachement(editPageModel, requestCycle);
		Mockito.verify(archiveService, Mockito.never()).content(Mockito.anyString());
	}

}
