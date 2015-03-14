package de.mq.archive.web;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.component.IRequestableComponent;

public interface ComponentFactory {

	<T> T newComponent(final OneWayMapping<?, Enum<?>> model, Enum<?> part, final Class<T> clazz);

	<T> T newComponent(final OneWayMapping<?, Enum<?>> model, Enum<?> part, final Class<T> clazz, final IModel<?> valueModel);

	<T> T newComponent(final String wicketId, final IModel<?> model, Class<T> clazz);

	<T,S> T deProxymize(final S fileUploadField, Class<? extends T> class1);

	

	<T extends IRequestableComponent> T componetByPath(IRequestableComponent parent, String path, Class<T> clazz);

	

}