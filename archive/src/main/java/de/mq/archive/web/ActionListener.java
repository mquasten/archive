package de.mq.archive.web;

@FunctionalInterface
public interface ActionListener {
	
	void process(final String id);

}
