package de.mq.archive.web.edit;

import java.util.Locale;





import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;

public class EditPageModelTest {
	@SuppressWarnings("unchecked")
	private final TwoWayMapping<Archive, Enum<?>> archiveModel = Mockito.mock(TwoWayMapping.class);
	@SuppressWarnings("unchecked")
	private final OneWayMapping<Locale, Enum<?>> labels = Mockito.mock(OneWayMapping.class);
	@SuppressWarnings("unchecked")
	private final OneWayMapping<Locale, Enum<?>> messages = Mockito.mock(OneWayMapping.class);
	@SuppressWarnings("unchecked")
	private final OneWayMapping<Locale, Enum<?>> attachementsLabels = Mockito.mock(OneWayMapping.class);
	
	private final EditPageModel editPageModel = new EditPageModelImpl(archiveModel, labels, messages,attachementsLabels );
	
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
	public final void  getArchiveModelWeb() {
		Assert.assertEquals(archiveModel, ((EditPageModelWeb)editPageModel).getArchiveModelWeb());
	}
	
	@Test
	public final void  getI18NLabels() {
		Assert.assertEquals(labels, ((EditPageModelWeb)editPageModel).getI18NLabels());
	}
	
	@Test
	public final void  getI18NMessages() {
		Assert.assertEquals(messages, ((EditPageModelWeb)editPageModel).getI18NMessages());
	}
	
	
}
