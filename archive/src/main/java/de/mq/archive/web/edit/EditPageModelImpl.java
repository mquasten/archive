package de.mq.archive.web.edit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.GridFsInfo;
import de.mq.archive.web.BasicEnumModelImpl;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;


class EditPageModelImpl implements EditPageModelWeb, EditPageModel {
	
	private final TwoWayMapping<Archive, Enum<?>> archiveModel;
	private final OneWayMapping<Locale, Enum<?>>  labels; 
	private final OneWayMapping<Locale, Enum<?>>  messages; 
	
	private final OneWayMapping<Locale, Enum<?>>  attachementLabels; 
	
	private final IModel<String> selectedAttachementWeb = new Model<>();; 
	
	
	private final List<TwoWayMapping<GridFsInfo<String>, Enum<?>>> attachements= new ArrayList<>(); 

	
	EditPageModelImpl(final TwoWayMapping<Archive, Enum<?>> archiveModel, final OneWayMapping<Locale, Enum<?>> labels, final OneWayMapping<Locale, Enum<?>> messages, final OneWayMapping<Locale, Enum<?>>  attachementLabels) {
		this.archiveModel = archiveModel;
		this.labels = labels;
		this.messages=messages;
		this.attachementLabels=attachementLabels;
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.web.edit.EditPageModel#getArchive()
	 */
	@Override
	public Archive getArchive() {
		
		return archiveModel.toDomain();
		
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.web.edit.EditPageModel#setArchive(de.mq.archive.domain.Archive)
	 */
	@Override
	public void setArchive(final Archive archive) {
		archiveModel.intoWeb(archive);
		attachements.clear();
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.web.edit.EditPageModel#add(de.mq.archive.domain.GridFsInfo)
	 */
	@Override
	public final void add(final GridFsInfo<String> attachement){
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final TwoWayMapping<GridFsInfo<String>, Enum<?>> model = new BasicEnumModelImpl<GridFsInfo<String>>(Arrays.asList(GridFsInfoParts.values()), (Class) GridFsInfo.class);
		model.intoWeb(attachement);
		attachements.add(model);
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
	

	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.web.edit.EditPageModelWeb#getAttachements()
	 */
	@Override
	public final IModel<List<TwoWayMapping<GridFsInfo<String>, Enum<?>>>> getAttachements() {
		return new ListModel<>(attachements);
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.web.edit.EditPageModelWeb#getI18NAttachementLabels()
	 */
	@Override
	public  final OneWayMapping<Locale, Enum<?>>  getI18NAttachementLabels() {
		return attachementLabels;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.web.edit.EditPageModelWeb#getSelectedAttachementWeb()
	 */
	@Override
	public final IModel<String> getSelectedAttachementWeb() {
		return selectedAttachementWeb;
	}

}
