package de.mq.archive.domain.support;

import java.util.Arrays;




import org.junit.Assert;
import org.junit.Test;

import de.mq.archive.domain.Category;

public class CategoryTest {
	
	@Test
	public final void coverage() {
		Arrays.stream(Category.values()).forEach(category -> Assert.assertEquals(category, Category.valueOf(category.name())));
	}

}
