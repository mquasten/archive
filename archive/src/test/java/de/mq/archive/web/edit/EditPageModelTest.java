package de.mq.archive.web.edit;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.GridFsInfo;
import de.mq.archive.domain.support.Constants;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;
import de.mq.archive.web.search.ArchiveModelParts;

public class EditPageModelTest {
	private static final String SELECTED_ATTACHEMENT_WEB_FIELD = "selectedAttachementWeb";
	private static final String EDITABLE_FIELD = "editable";
	private static final String ATTACHEMENTS_FIELD = "attachements";
	private static final String CONTENT_TYPE = "audio/mpeg";
	private static final Date DATE = new Date();
	private static final double LENGTH = 3e6;
	private static final String ID = "19680528";
	private static final String FILE_NAME = "whereTheWildRosesGrow.mp3";
	@SuppressWarnings("unchecked")
	private final TwoWayMapping<Archive, Enum<?>> archiveModel = Mockito.mock(TwoWayMapping.class);
	@SuppressWarnings("unchecked")
	private final OneWayMapping<Locale, Enum<?>> labels = Mockito.mock(OneWayMapping.class);
	@SuppressWarnings("unchecked")
	private final OneWayMapping<Locale, Enum<?>> messages = Mockito.mock(OneWayMapping.class);
	@SuppressWarnings("unchecked")
	private final OneWayMapping<Locale, Enum<?>> attachementsLabels = Mockito.mock(OneWayMapping.class);

	private final EditPageModel editPageModel = new EditPageModelImpl(archiveModel, labels, messages, attachementsLabels);

	private final Archive archive = Mockito.mock(Archive.class);

	@Test
	public final void getArchive() {
		Mockito.when(archiveModel.toDomain()).thenReturn(archive);
		Assert.assertEquals(archive, editPageModel.getArchive());
	}

	@Test
	public final void setArchive() {
		editPageModel.setArchive(archive);
		Mockito.verify(archiveModel).intoWeb(archive);
	}

	@Test
	public final void getArchiveModelWeb() {
		Assert.assertEquals(archiveModel, ((EditPageModelWeb) editPageModel).getArchiveModelWeb());
	}

	@Test
	public final void getI18NLabels() {
		Assert.assertEquals(labels, ((EditPageModelWeb) editPageModel).getI18NLabels());
	}

	@Test
	public final void getI18NMessages() {
		Assert.assertEquals(messages, ((EditPageModelWeb) editPageModel).getI18NMessages());
	}

	@Test
	public final void setEditable() {
		Assert.assertFalse((Boolean) ReflectionTestUtils.getField(editPageModel, EDITABLE_FIELD));
		editPageModel.setEditable(true);
		Assert.assertTrue((Boolean) ReflectionTestUtils.getField(editPageModel, EDITABLE_FIELD));
	}

	@Test
	public final void add() {

		final GridFsInfo<String> attachement = Constants.create(ID, FILE_NAME, LENGTH, DATE, CONTENT_TYPE);

		editPageModel.add(attachement);

		final List<TwoWayMapping<GridFsInfo<String>, Enum<?>>> attachements = attachements();
		final TwoWayMapping<GridFsInfo<String>, Enum<?>> result = attachements.stream().findFirst().get();
		Assert.assertEquals(FILE_NAME, result.part(GridFsInfoParts.Filename).getObject());
		Assert.assertEquals(ID, result.part(GridFsInfoParts.Id).getObject());
		Assert.assertEquals(LENGTH, result.part(GridFsInfoParts.ContentLength).getObject());
		Assert.assertEquals(DATE, result.part(GridFsInfoParts.LastModified).getObject());
		Assert.assertEquals(CONTENT_TYPE, result.part(GridFsInfoParts.ContentType).getObject());
	}

	@SuppressWarnings({ "unchecked" })
	private List<TwoWayMapping<GridFsInfo<String>, Enum<?>>> attachements() {
		return (List<TwoWayMapping<GridFsInfo<String>, Enum<?>>>) ReflectionTestUtils.getField(editPageModel, ATTACHEMENTS_FIELD);
	}

	@Test
	public final void getAttachements() {
		final List<TwoWayMapping<GridFsInfo<String>, Enum<?>>> attachements = attachements();
		@SuppressWarnings("unchecked")
		final TwoWayMapping<GridFsInfo<String>, Enum<?>> attachement = Mockito.mock(TwoWayMapping.class);
		attachements.add(attachement);
		Assert.assertEquals(attachements, ((EditPageModelWeb) editPageModel).getAttachements().getObject());
	}

	@Test
	public final void getI18NAttachementLabels() {
		Assert.assertEquals(attachementsLabels, ((EditPageModelWeb) editPageModel).getI18NAttachementLabels());
	}

	@Test
	public final void getSelectedAttachementWeb() {
		@SuppressWarnings("unchecked")
		final IModel<String> selectedAttachmentModel = Mockito.mock(IModel.class);
		ReflectionTestUtils.setField(editPageModel, SELECTED_ATTACHEMENT_WEB_FIELD, selectedAttachmentModel);
		Assert.assertEquals(selectedAttachmentModel, ((EditPageModelWeb) editPageModel).getSelectedAttachementWeb());
	}

	@Test
	public final void getSelectedAttachementIdNothingSelected() {
		Assert.assertTrue(editPageModel.getSelectedAttachementId().isEmpty());
	}

	@Test
	public final void getSelectedAttachementId() {
		@SuppressWarnings("unchecked")
		final IModel<String> selectedAttachmentModel = Mockito.mock(IModel.class);
		ReflectionTestUtils.setField(editPageModel, SELECTED_ATTACHEMENT_WEB_FIELD, selectedAttachmentModel);
		Mockito.when(selectedAttachmentModel.getObject()).thenReturn(ID);
		Assert.assertEquals(ID, editPageModel.getSelectedAttachementId());
	}

	@Test
	public final void isPersistent() {

		@SuppressWarnings("unchecked")
		final IModel<Serializable> model = Mockito.mock(Model.class);
		Mockito.when(model.getObject()).thenReturn(ID);
		Mockito.when(archiveModel.part(ArchiveModelParts.Id)).thenReturn(model);
		Assert.assertTrue(editPageModel.isPersistent());
		Mockito.when(model.getObject()).thenReturn(null);
		Assert.assertFalse(editPageModel.isPersistent());
	}

	@Test
	public final void hasAttachements() {
		Assert.assertFalse(((EditPageModelWeb) editPageModel).hasAttachements());
		@SuppressWarnings("unchecked")
		final TwoWayMapping<GridFsInfo<String>, Enum<?>> attachement = Mockito.mock(TwoWayMapping.class);
		attachements().add(attachement);
		Assert.assertTrue(((EditPageModelWeb) editPageModel).hasAttachements());
	}

	@Test
	public final void isAttachementSelected() {
		Assert.assertFalse(((EditPageModelWeb) editPageModel).isAttachementSelected());
		@SuppressWarnings("unchecked")
		final IModel<String> selectedAttachmentModel = Mockito.mock(IModel.class);
		Mockito.when(selectedAttachmentModel.getObject()).thenReturn(ID);
		ReflectionTestUtils.setField(editPageModel, SELECTED_ATTACHEMENT_WEB_FIELD, selectedAttachmentModel);
		Assert.assertTrue(((EditPageModelWeb) editPageModel).isAttachementSelected());
	}

	@Test
	public final void changeable() {
		@SuppressWarnings("unchecked")
		final IModel<Serializable> model = Mockito.mock(Model.class);
		Mockito.when(model.getObject()).thenReturn(ID);
		Mockito.when(archiveModel.part(ArchiveModelParts.Id)).thenReturn(model);

		editPageModel.setEditable(true);

		Assert.assertTrue(((EditPageModelWeb) editPageModel).changeable());
		editPageModel.setEditable(false);

		Assert.assertFalse(((EditPageModelWeb) editPageModel).changeable());
		editPageModel.setEditable(true);
		Mockito.when(model.getObject()).thenReturn(null);
		Assert.assertFalse(((EditPageModelWeb) editPageModel).changeable());
		editPageModel.setEditable(false);
		Assert.assertFalse(((EditPageModelWeb) editPageModel).changeable());
	}

	@Test
	public final void canBeSaved() {
		editPageModel.setEditable(true);
		Assert.assertTrue(((EditPageModelWeb) editPageModel).canBeSaved());
		@SuppressWarnings("unchecked")
		final IModel<Serializable> model = Mockito.mock(Model.class);
		Mockito.when(model.getObject()).thenReturn(ID);
		Mockito.when(archiveModel.part(ArchiveModelParts.Id)).thenReturn(model);
		editPageModel.setEditable(false);
		Assert.assertFalse(((EditPageModelWeb) editPageModel).canBeSaved());
		Mockito.when(model.getObject()).thenReturn(null);
		Assert.assertTrue(((EditPageModelWeb) editPageModel).canBeSaved());
		editPageModel.setEditable(true);
		Assert.assertTrue(((EditPageModelWeb) editPageModel).canBeSaved());
	}

}
