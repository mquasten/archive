package de.mq.archive.web.edit;

import java.util.Arrays;

import org.apache.wicket.markup.html.basic.Label;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

import de.mq.archive.web.ActionButton;

public class I18NAttachementsModelPartsTest {
	
	private static final String LABEL_POSTFIX = "Header";
	private static final String BUTTON_POSTFIX = "Button";

	@Test
	public final void create() {
		Arrays.asList(I18NAttachementsModelParts.values()).forEach(value -> Assert.assertEquals(value, I18NAttachementsModelParts.valueOf(value.name())));
	}
	@Test
	public final void key() {
		Arrays.asList(I18NAttachementsModelParts.values()).forEach(value -> Assert.assertTrue((value.key().startsWith("archive_edit_attachement_"))));
	}
	
	@Test
	public final void wicketId() {
		Arrays.asList(I18NAttachementsModelParts.values()).forEach(value ->Assert.assertEquals(StringUtils.uncapitalize(value.name()), value.wicketId()));
	}
	
	@Test
	public final void  targetClass() {
		//System.out.println("aaa");
		Arrays.asList(I18NAttachementsModelParts.values()).stream().filter(value -> value.name().endsWith(BUTTON_POSTFIX)).forEach(value -> Assert.assertTrue(ActionButton.class.equals(value.targetClass())));
		Arrays.asList(I18NAttachementsModelParts.values()).stream().filter(value -> value.name().endsWith(LABEL_POSTFIX)).forEach(value -> Assert.assertTrue(Label.class.equals(value.targetClass())));
	
		Arrays.asList(I18NAttachementsModelParts.values()).forEach(value -> Assert.assertTrue(value.name().endsWith(BUTTON_POSTFIX)||value.name().endsWith(LABEL_POSTFIX)));
	}
	

}
