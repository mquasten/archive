package de.mq.archive.web.search;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class I18NSearchPageModelPartsTest {
	
	@Test
	public final void parts() {
		Arrays.stream(I18NSearchPageModelParts.values()).filter(part -> part != I18NSearchPageModelParts.ApplicationHeadline).forEach(part -> Assert.assertTrue(part.key.startsWith("archive_search")));
	}
	
	@Test
	public final void coverage() {
		Arrays.stream(I18NSearchPageModelParts.values()).forEach(part -> Assert.assertNotNull(I18NSearchPageModelParts.valueOf(part.name())));
	}

}
