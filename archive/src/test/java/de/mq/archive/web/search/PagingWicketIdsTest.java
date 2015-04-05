package de.mq.archive.web.search;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;



public class PagingWicketIdsTest {
	
	@Test
	public final void wicketIds() {
		Arrays.stream( PagingWicketIds.values()).forEach(part -> Assert.assertEquals(StringUtils.uncapitalize(part.name()), part.wicketId()));
	}
	
	@Test
	public final void coverage() {
		Arrays.stream( PagingWicketIds.values()).forEach(part -> Assert.assertEquals(part, PagingWicketIds.valueOf(part.name())));
	}

}
