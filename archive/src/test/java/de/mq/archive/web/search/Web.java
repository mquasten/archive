package de.mq.archive.web.search;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.mq.archive.web.ActionImageButtonTest;
import de.mq.archive.web.edit.EditPageControllerTest;
import de.mq.archive.web.edit.EditPageModelTest;
import de.mq.archive.web.edit.EditPageModelsTest;
import de.mq.archive.web.edit.EditPageTest;
import de.mq.archive.web.edit.GridFsInfoPartsTest;
import de.mq.archive.web.edit.I18NAttachementsModelPartsTest;
import de.mq.archive.web.edit.I18NEditPageMessagesPartsTest;
import de.mq.archive.web.edit.I18NEditPageModelPartsTest;

@RunWith(Suite.class)
@SuiteClasses({ ArchiveModelPartsTest.class, I18NSearchPageModelPartsTest.class, ModelsTest.class, SearchPageControllerTest.class, SearchPageModelTest.class, WicketApplicationTest.class, SearchPageTest.class,
	EditPageControllerTest.class, EditPageModelTest.class, EditPageModelsTest.class, I18NEditPageMessagesPartsTest.class, I18NEditPageModelPartsTest.class , 
	EditPageTest.class, I18NAttachementsModelPartsTest.class, GridFsInfoPartsTest.class, SearchPageControllerTest.class, ActionImageButtonTest.class, PagingWicketIdsTest.class })
public class Web {

}
