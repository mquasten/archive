package de.mq.archive.web;

import java.io.Serializable;



@FunctionalInterface
public interface ActionListener extends Serializable  {
	
	void process(final String id);

}
