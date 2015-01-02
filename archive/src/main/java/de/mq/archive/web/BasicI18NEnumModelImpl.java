package de.mq.archive.web;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;

public class BasicI18NEnumModelImpl implements OneWayStringMapping<Locale, Enum<?>> {

	
	private static final long serialVersionUID = 1L;
	private  final Map<Enum<?>, Entry<String,IModel<String>>> models = new HashMap<>();
	
	private final MessageSource messageSource; 
	
	public BasicI18NEnumModelImpl(final MessageSource messageSource, final List<Enum<?>> values, final List<String>keys) {
		this.messageSource=messageSource;
		values.forEach( part -> models.put(part,  new AbstractMap.SimpleEntry<>(keys.get(part.ordinal()), new Model<>())));
	}
	
	@Override
	public void intoWeb(final Locale locale) {
		models.values().forEach(entry -> entry.getValue().setObject(messageSource.getMessage(entry.getKey(), null, locale)));
		
		
	}

	@Override
	public IModel<String> part(final Enum<?> part) {
		Assert.notNull(part);
		final Entry<String,IModel<String>> entry =    models.get(part);
		Assert.notNull( entry, String.format("No part model defined for %s", part));
		return entry.getValue();
	}

}
