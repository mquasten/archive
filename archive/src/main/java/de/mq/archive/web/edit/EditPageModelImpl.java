package de.mq.archive.web.edit;

import java.util.Locale;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;


class EditPageModelImpl implements EditPageModelWeb, EditPageModel {
	
	private final TwoWayMapping<Archive, Enum<?>> archiveModel;
	private final OneWayMapping<Locale, Enum<?>>  labels; 
	private final OneWayMapping<Locale, Enum<?>>  messages; 

	
	EditPageModelImpl(final TwoWayMapping<Archive, Enum<?>> archiveModel, final OneWayMapping<Locale, Enum<?>> labels, final OneWayMapping<Locale, Enum<?>> messages) {
		this.archiveModel = archiveModel;
		this.labels = labels;
		this.messages=messages;
	}
	
	@Override
	public Archive getArchive() {
		
		return archiveModel.toDomain();
		
	}
	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.edit.EditPageModelWeb#getArchiveModelWeb()
	 */
	@Override
	public final TwoWayMapping<Archive, Enum<?>> getArchiveModelWeb() {
		return archiveModel;
	}
	
	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.edit.EditPageModelWeb#getI18NLabels()
	 */
	@Override
	public  final OneWayMapping<Locale, Enum<?>>  getI18NLabels() {
		return labels;
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.web.edit.EditPageModelWeb#getI18NMessages()
	 */
	@Override
	public  final OneWayMapping<Locale, Enum<?>>  getI18NMessages() {
		return messages;
	}

}
