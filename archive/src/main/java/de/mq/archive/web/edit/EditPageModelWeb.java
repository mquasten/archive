package de.mq.archive.web.edit;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.model.IModel;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.GridFsInfo;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;

public interface EditPageModelWeb {

	TwoWayMapping<Archive, Enum<?>> getArchiveModelWeb();

	OneWayMapping<Locale, Enum<?>> getI18NLabels();

	OneWayMapping<Locale, Enum<?>> getI18NMessages();

	IModel<List<TwoWayMapping<GridFsInfo<String>, Enum<?>>>> getAttachements();

	OneWayMapping<Locale, Enum<?>> getI18NAttachementLabels();

	IModel<String> getSelectedAttachementWeb();

	boolean changeable();

	boolean hasAttachements();

	boolean isAttachementSelected();

	boolean canBeSaved();

	//boolean isPersistent();

	//boolean hasAttachements();

	//boolean isAttachementSelected();

	

}