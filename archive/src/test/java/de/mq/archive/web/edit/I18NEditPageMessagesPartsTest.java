package de.mq.archive.web.edit;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

public class I18NEditPageMessagesPartsTest {
	
	private static final String ARCHIVE_EDIT = "archive_edit";
	private static final String MESSAGE = "message";
	@Test
	public final void wicketId() {
		
		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(p -> Assert.assertEquals(StringUtils.uncapitalize(p.name()) + I18NEditPageMessagesParts.MESSAGE , p.wicketId()));
	}
	@Test
	public final void wicketIdFeedback() {
		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(p -> Assert.assertEquals(StringUtils.uncapitalize(p.name()) + I18NEditPageMessagesParts.FEEDBACK , p.wicketIdFeedback()));
	}
	
	@Test
	public final void wicketIdInput() {
		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(p -> Assert.assertEquals(StringUtils.uncapitalize(p.name())  , p.wicketIdInput()));
	}
	
	@Test
	public final void key() {
		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(p -> Assert.assertTrue(p.key().startsWith(ARCHIVE_EDIT)&&p.key().endsWith(MESSAGE))) ;
	}
	@Test
	public final void create() {
		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(p -> Assert.assertEquals(p, I18NEditPageMessagesParts.valueOf(p.name())));
	}

}
