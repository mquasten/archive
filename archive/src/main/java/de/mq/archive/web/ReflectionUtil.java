package de.mq.archive.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;










import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.util.ReflectionUtils;


public interface ReflectionUtil {
	
	@SuppressWarnings("unchecked")
	static <T> T getFieldValue(final Object source, final Class<? extends Annotation> annotionClass) {
		final Set<Field> fields = new HashSet<>();
		ReflectionUtils.doWithFields(source.getClass(), f -> fields.add(f), f-> f.isAnnotationPresent(annotionClass));
		DataAccessUtils.requiredSingleResult(fields);
		final Field field = fields.stream().findFirst().get();
		field.setAccessible(true);
		return (T) ReflectionUtils.getField(field, source);
	
		
	}

}
