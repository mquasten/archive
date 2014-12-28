package de.mq.archive.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public interface Archive  extends Serializable{

	String id();

	String name();

	Category category();

	String text();

	Date documentDate();

	Set<String> relatedPersons();

}