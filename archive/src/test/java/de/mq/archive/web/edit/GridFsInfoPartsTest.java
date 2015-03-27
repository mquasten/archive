package de.mq.archive.web.edit;

import java.util.Arrays;



import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

public class GridFsInfoPartsTest {
	@Test
	public final void create() {
		Arrays.asList(GridFsInfoParts.values()).forEach(value -> Assert.assertEquals(value, GridFsInfoParts.valueOf(value.name())));
	}
	
	@Test
	public final void wicketId(){
		Arrays.asList(GridFsInfoParts.values()).forEach(value -> Assert.assertEquals(StringUtils.uncapitalize(value.name()), value.wicketId()));
	}

}
