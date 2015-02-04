package de.mq.archive.web;

import org.apache.wicket.model.IModel;

public interface ComponentFactory {

	<T> T newComponent(final OneWayMapping<?, Enum<?>> model, Enum<?> part, final Class<T> clazz);

	<T> T newComponent(final OneWayMapping<?, Enum<?>> model, Enum<?> part, final Class<T> clazz, final IModel<?> valueModel);

	<T> T newComponent(final String wicketId, final IModel<?> model, Class<T> clazz);

}