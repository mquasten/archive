package de.mq.archive.domain.support;

import java.util.Date;
import java.util.GregorianCalendar;




import org.junit.Assert;
import org.junit.Test;

import de.mq.archive.domain.GridFsInfo;

public class GridFsInfoTest {
	
	private static final String CONTENT_TYPE = "audio/mpeg";
	private static final Date DATE = new GregorianCalendar(1995, 9, 2 ).getTime();
	private static final double CONTENT_LENGTH = 3e6;
	private static final String FILE_NAME = "whereTheWildRosesGrow.mp3";
	private static final String ID = "19680528";
	private final GridFsInfo<String> gridFsInfo = new GridFsInfoImpl(ID, FILE_NAME, CONTENT_LENGTH, DATE, CONTENT_TYPE );
	
	@Test
	public final void values() {
		Assert.assertEquals(ID, gridFsInfo.id());
		Assert.assertEquals(FILE_NAME, gridFsInfo.filename());
		Assert.assertEquals(CONTENT_LENGTH, gridFsInfo.contentLength());
		Assert.assertEquals(DATE, gridFsInfo.lastModified());
		Assert.assertEquals(CONTENT_TYPE, gridFsInfo.contentType());
	}


}
