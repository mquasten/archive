package de.mq.archive.web.search;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import org.springframework.util.StringUtils;

import de.mq.archive.domain.support.ArchiveImpl;

public class ArchiveModelPartsTest {
	
	@Test
	public final void parts() {
		final Collection<String> names = new ArrayList<>();
		ReflectionUtils.doWithFields(ArchiveImpl.class, field -> names.add(StringUtils.capitalize(field.getName())) ,field -> ! Modifier.isStatic(field.getModifiers()));
		names.forEach(name -> Assert.assertNotNull(ArchiveModelParts.valueOf(name)) );
	   Assert.assertEquals(names.size(), ArchiveModelParts.values().length);
	}
	
	

}
