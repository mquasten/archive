package de.mq.archive.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;




import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public class SimpleParameterInjectionActionListenerImpl implements ActionListener{

	
	private static final long serialVersionUID = 1L;

	private final Object controller; 
	
	private final BeanFactory beanFactory;
	
	final Map<String, Method> methods = new HashMap<>();
	public SimpleParameterInjectionActionListenerImpl(final BeanFactory beanfactory, final Class<?> controllerClass) {
		this.controller=beanfactory.getBean(controllerClass);
		Assert.notNull(controller, String.format("ControllerBean not found for class %s", controllerClass));
		this.beanFactory=beanfactory;
		ReflectionUtils.doWithMethods(controller.getClass(), m-> methods.put(m.getAnnotation(Named.class).value().trim().isEmpty() ? m.getName() : m.getAnnotation(Named.class).value(), m), m -> m.isAnnotationPresent(Named.class)  );
	}
	
	@Override
	public void process(final String id) {
		if( ! methods.containsKey(id)){
			System.out.println(String.format("No actions for %s", id));
			return;
		}
		final Method method = methods.get(id);
		method.setAccessible(true);
		final List<Object> args = new ArrayList<>();
		Arrays.asList(method.getParameters()).forEach(p ->args.add(beanFactory.getBean(p.getType())));
		try {
			
			method.invoke(controller, args.toArray(new Object[args.size()]));
		} catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new IllegalStateException(String.format("Error ececuting actions for %s", id), ex);
		}
	}

}
