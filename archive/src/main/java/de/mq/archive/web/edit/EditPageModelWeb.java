package de.mq.archive.web.edit;

import java.util.Locale;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;

public interface EditPageModelWeb {

	TwoWayMapping<Archive, Enum<?>> getArchiveModelWeb();

	OneWayMapping<Locale, Enum<?>> getI18NLabels();

	OneWayMapping<Locale, Enum<?>> getI18NMessages();

}