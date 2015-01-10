package de.mq.archive.web.search;

import java.util.Map;
import java.util.Map.Entry;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.wicket.model.IModel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;


public class BasicI18NEnumModelTest {
	
	private final MessageSource messageSource = Mockito.mock(MessageSource.class);
	
	@Test
	public final void create() {
		final I18NSearchPageModel i18nSearchPageModel = new I18NSearchPageModelImpl(messageSource); 
	
		@SuppressWarnings("unchecked")
		final Map<I18NSearchPageModelParts,Entry<String, IModel<String>>> models = (Map<I18NSearchPageModelParts, Entry<String, IModel<String>>>) ReflectionTestUtils.getField(i18nSearchPageModel, "models");
	
		Assert.assertEquals(Stream.of(I18NSearchPageModelParts.values()).collect(Collectors.toSet()),models.keySet());
		models.keySet().forEach(key -> Assert.assertEquals(key.key, models.get(key).getKey()));
		models.keySet().forEach(key -> Assert.assertTrue(models.get(key).getValue() instanceof IModel));
		
	}

}
