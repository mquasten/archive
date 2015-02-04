package de.mq.archive.web.edit;

import javax.inject.Named;

@Named
public class EditPageControllerImpl implements EditPageController {
	
	@Named(SAVE_ACTION)
	public final void save(final EditPageModel model) {
		System.out.println(model.getArchive().name());
		System.out.println(model.getArchive().archiveId());
		System.out.println(model.getArchive().category());
		System.out.println(model.getArchive().documentDate());
		System.out.println(model.getArchive().text());
		
	}
}
