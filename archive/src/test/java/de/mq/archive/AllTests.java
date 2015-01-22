package de.mq.archive;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.mq.archive.domain.support.DomainTests;
import de.mq.archive.web.Util;
import de.mq.archive.web.search.Web;

@RunWith(Suite.class)
@SuiteClasses({ DomainTests.class,  Util.class, Web.class })
public class AllTests {

}
