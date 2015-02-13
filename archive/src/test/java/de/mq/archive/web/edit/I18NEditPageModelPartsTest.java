package de.mq.archive.web.edit;

import java.util.Arrays;






import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

public class I18NEditPageModelPartsTest {
	
	private static final String BUTTON = "button";
	private static final String HEADLINE = "headline";
	private static final String KEY_PREFIX = "archive_";

	@Test
	public final void wicketId() {
		
		Arrays.stream(I18NEditPageModelParts.values()).forEach(p -> Assert.assertEquals(StringUtils.uncapitalize(p.name()), p.wicketId()));
	}
	
	@Test
	public final void key() {
		Arrays.stream(I18NEditPageModelParts.values()).forEach(p -> Assert.assertTrue(p.key().startsWith(KEY_PREFIX)));
	}
	
	@Test
	public final void isWithInForm() {
		System.out.println();
		Arrays.stream(I18NEditPageModelParts.values()).filter(p -> !p.key().contains(HEADLINE)).forEach(	p -> Assert.assertTrue(p.isWithInForm()));
	}
	
	@Test
	public final void isButton() {
		Arrays.stream(I18NEditPageModelParts.values()).forEach(	p -> Assert.assertEquals(p.key().contains(BUTTON), p.isButton()));
	}
	@Test
	public final void create() {
		Arrays.stream(I18NEditPageModelParts.values()).forEach(p -> Assert.assertEquals(p, I18NEditPageModelParts.valueOf(p.name())));
	}

}
