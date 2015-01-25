package de.mq.archive.web;

import org.apache.wicket.model.IModel;

public interface ComponentFactory {

	public abstract <T> T newComponent(OneWayMapping<?, Enum<?>> model, Enum<?> part, Class<T> clazz);

	public abstract <T> T newComponent(OneWayMapping<?, Enum<?>> model, Enum<?> part, Class<T> clazz, IModel<?> valueModel);

	public abstract <T> T newComponent(String wicketId, IModel<?> model, Class<T> clazz);

}