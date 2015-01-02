package de.mq.archive.web;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class BasicEnumModelImpl<Domain> implements  EnumModel<Domain> {
	
	private static final long serialVersionUID = 1L;
	private  final Map<Enum<?>,IModel<?>> models = new HashMap<>();
	
	private Class<? extends Domain> clazz;
	
	protected BasicEnumModelImpl(final List<Enum<?>> values, final Class<? extends Domain> clazz) {
		values.forEach( part ->  models.put(part, new Model<>()));
		this.clazz=clazz;
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.EnumModel#intoWeb(Domain)
	 */

	@Override
	public void intoWeb(final Domain source) {
		models.keySet().forEach( part -> { 
		
			final String fieldName = StringUtils.uncapitalize(part.name());
			final Field field = ReflectionUtils.findField(source.getClass(), fieldName);
			field.setAccessible(true);
			
			final IModel<Object> model = part(part, Object.class);
			final Object value = ReflectionUtils.getField(field, source);
			model.setObject(value);
		} );
	
		
	}

	
	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.EnumModel#toDomain()
	 */
	
	@Override
	public Domain toDomain() {
		final Domain archive = (Domain) BeanUtils.instantiateClass(clazz);
		models.entrySet().forEach(entry -> { 
			final String fieldName = StringUtils.uncapitalize(entry.getKey().name());
			final Field field = ReflectionUtils.findField(archive.getClass(), fieldName);
			Assert.notNull(field);
			field.setAccessible(true);
			ReflectionUtils.setField(field, archive, entry.getValue().getObject());
			
		});
		
		return archive;
	}

	


	/* (non-Javadoc)
	 * @see de.mq.archive.web.search.EnumModel#part(java.lang.Enum, java.lang.Class)
	 */
	
	@Override
	public <T> IModel<T> part(Enum<?> part, Class<T> clazz) {
		Assert.notNull(part);
		@SuppressWarnings("unchecked")
		final IModel<T> model =   (IModel<T>) models.get(part);
		Assert.notNull( model, String.format("No part model defined for %s", part));
		return model;
	}

	
	
}
