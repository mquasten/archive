package de.mq.archive.web.search;

import org.apache.wicket.model.IModel;

interface EnumModel<Domain> {

	void intoWeb(Domain source);

	Domain toDomain();

	<T> IModel<T> part(Enum<?> part, Class<T> clazz);

}