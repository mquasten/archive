package de.mq.archive.web.edit;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import de.mq.archive.domain.ArchiveService;

@Named
public class EditPageControllerImpl implements EditPageController {
	
	
	private final ArchiveService archiveService;
	
	@Autowired
	EditPageControllerImpl(final ArchiveService archiveService) {
		this.archiveService=archiveService; 
	}
	
	
	
	@Named(SAVE_ACTION)
	public final void save(final EditPageModel model) {
		archiveService.save(model.getArchive());
		
	}
}
