package de.mq.archive.web;

import org.apache.wicket.model.IModel;

public interface EnumModel<Domain> extends TwoWayMapping<Domain, Enum<?>>{

	void intoWeb(Domain source);

	Domain toDomain();

	<T> IModel<T> part(Enum<?> part, Class<T> clazz);

}