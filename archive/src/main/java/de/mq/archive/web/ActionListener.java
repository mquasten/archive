package de.mq.archive.web;

import java.io.Serializable;

@FunctionalInterface
public interface ActionListener<T> extends Serializable {
	
	void process(final T id);

}
