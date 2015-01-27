package de.mq.archive.web.edit;

import java.util.Locale;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;


class EditPageModelImpl implements EditPageModelWeb {
	
	private final TwoWayMapping<Archive, Enum<?>> archiveModel;
	private final OneWayMapping<Locale, Enum<?>>  labels; 
	
	EditPageModelImpl(TwoWayMapping<Archive, Enum<?>> archiveModel, OneWayMapping<Locale, Enum<?>> labels) {
		this.archiveModel = archiveModel;
		this.labels = labels;
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

}
