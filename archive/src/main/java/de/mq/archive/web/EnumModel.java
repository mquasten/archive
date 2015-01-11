package de.mq.archive.web;

import org.apache.wicket.model.IModel;

public interface EnumModel<Domain> extends TwoWayMapping<Domain, Enum<?>>{

	void intoWeb(final Domain source);

	Domain toDomain();

	<T> IModel<T> part(final Enum<?> part, final Class<T> clazz);

}