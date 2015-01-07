package de.mq.archive.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface Archive  extends Serializable{

	String id();

	String name();

	Category category();

	String text();

	Date documentDate();

	List<String> relatedPersons();

	String archiveId();

	void assign(final String person);

	void remove(final String person);

}