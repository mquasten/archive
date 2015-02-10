package de.mq.archive.web.search;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.mq.archive.web.edit.EditPageControllerTest;
import de.mq.archive.web.edit.EditPageModelTest;

@RunWith(Suite.class)
@SuiteClasses({ ArchiveModelPartsTest.class, I18NSearchPageModelPartsTest.class, ModelsTest.class, SearchPageControllerTest.class, SearchPageModelTest.class, WicketApplicationTest.class, SearchPageTest.class, EditPageControllerTest.class, EditPageModelTest.class })
public class Web {

}
