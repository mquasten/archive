package de.mq.archive.web;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.wicket.model.IModel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.archive.web.search.I18NSearchPageModelParts;

public class I18NEnumModelTest {

	private static final String MESSAGE_SOURCE_FIELD = "messageSource";
	private static final String MODELS_FIELD = "models";
	private final MessageSource messageSource = Mockito.mock(MessageSource.class);
	final OneWayMapping<Locale, Enum<?>> oneWayMapping = new BasicI18NEnumModelImpl(messageSource, Arrays.asList(I18NSearchPageModelParts.values()), Arrays.asList(I18NSearchPageModelParts.values()).stream().map(p -> p.key()).collect(Collectors.toList()));

	@Test
	public final void create() {
		Assert.assertEquals(messageSource, ReflectionTestUtils.getField(oneWayMapping, MESSAGE_SOURCE_FIELD));

		final Map<Enum<I18NSearchPageModelParts>, Entry<String, IModel<String>>> models = models();

		models.entrySet().forEach(entry -> Assert.assertEquals((((I18NSearchPageModelParts) entry.getKey()).key()), entry.getValue().getKey()));
		models.values().forEach(entry -> Assert.assertNull(entry.getValue().getObject()));
		models.values().forEach(entry -> Assert.assertTrue(entry.getValue() instanceof IModel));

	}

	@SuppressWarnings("unchecked")
	private Map<Enum<I18NSearchPageModelParts>, Entry<String, IModel<String>>> models() {
		return (Map<Enum<I18NSearchPageModelParts>, Entry<String, IModel<String>>>) ReflectionTestUtils.getField(oneWayMapping, MODELS_FIELD);
	}

	@Test
	public final void intoWeb() {

		Arrays.asList(I18NSearchPageModelParts.values()).stream().forEach(part -> Mockito.when(messageSource.getMessage(part.key(), null, Locale.GERMAN)).thenReturn(part.key()));
		oneWayMapping.intoWeb(Locale.GERMAN);
		final Map<Enum<I18NSearchPageModelParts>, Entry<String, IModel<String>>> models = models();
		models.values().forEach(entry -> Assert.assertEquals(entry.getKey(), entry.getValue().getObject()));

	}

	@Test
	public final void part() {
		Arrays.asList(I18NSearchPageModelParts.values()).stream().forEach(part -> Mockito.when(messageSource.getMessage(part.key(), null, Locale.GERMAN)).thenReturn(part.key()));
		oneWayMapping.intoWeb(Locale.GERMAN);

		Arrays.asList(I18NSearchPageModelParts.values()).forEach(part -> Assert.assertEquals(part.key(), oneWayMapping.part(part).getObject()));

	}

}
