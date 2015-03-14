package de.mq.archive.web;

import java.lang.reflect.Constructor;

import javax.inject.Named;

import org.apache.wicket.model.IModel;
import org.apache.wicket.proxy.ILazyInitProxy;
import org.apache.wicket.request.component.IRequestableComponent;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import de.mq.archive.web.search.WicketIdAware;

@Named
class SimpleComponentFactoryImpl implements ComponentFactory {
	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.ComponentFactory#newComponent(de.mq.archive.web.OneWayMapping, java.lang.Enum, java.lang.Class)
	 */
	@Override
	public final <T> T newComponent(final OneWayMapping<?, Enum<?>> model, final Enum<?> part, final Class<T> clazz) {
		return BeanUtils.instantiateClass(constructor(clazz, String.class, IModel.class), ((WicketIdAware) part).wicketId(), model.part(part));
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.web.ComponentFactory#newComponent(de.mq.archive.web.OneWayMapping, java.lang.Enum, java.lang.Class, org.apache.wicket.model.IModel)
	 */
	@Override
	public final <T> T newComponent(final OneWayMapping<?, Enum<?>> model, final Enum<?> part, final Class<T> clazz, final IModel<?> valueModel) {
		return BeanUtils.instantiateClass(constructor(clazz, String.class, IModel.class, IModel.class), ((WicketIdAware) part).wicketId(), model.part(part), valueModel);
	}

	/* (non-Javadoc)
	 * @see de.mq.archive.web.ComponentFactory#newComponent(java.lang.String, org.apache.wicket.model.IModel, java.lang.Class)
	 */
	@Override
	public final <T> T newComponent(String wicketId, final IModel<?> model, final Class<T> clazz) {
	
		return BeanUtils.instantiateClass(constructor(clazz, String.class, IModel.class), wicketId, model);
	}
	/*
	 * (non-Javadoc)
	 * @see de.mq.archive.web.ComponentFactory#deProxymize(java.lang.Object, java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final <T,S> T deProxymize(final S proxy, final Class<? extends T> target) {
		Assert.isInstanceOf(ILazyInitProxy.class, proxy);
		 return (T)  ((ILazyInitProxy) proxy).getObjectLocator().locateProxyTarget();
	}

	private <T> Constructor<T> constructor(final Class<T> clazz, final Class<?>... types) {
		try {
			return clazz.getConstructor(types);
		} catch (final Exception ex) {
			throw new IllegalStateException("Unable to create Component", ex);
		}

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final  <T extends IRequestableComponent>   T componetByPath(final IRequestableComponent parent , final String path, final Class<T> clazz) {
		Assert.notNull(parent, "ParentComponent should be given");
		Assert.hasText(path, "Path should be given");
		final IRequestableComponent result = parent.get(path);
		Assert.notNull(result, String.format("Component %s not found on parent %s",   path , parent.getId() ));
		return (T) result;
		
	}

	
}
