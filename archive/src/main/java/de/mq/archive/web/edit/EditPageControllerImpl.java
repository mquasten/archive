package de.mq.archive.web.edit;

import javax.inject.Named;





import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.web.search.SearchPageModel;

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
	
	@Named(SearchPageModel.INIT_EDIT)
	public final void init(final SearchPageModel searchPageModel, final EditPageModel model) {
		model.setArchive(archiveService.archive(searchPageModel.getSelectedArchiveId()));
	}
	
	@Named(SearchPageModel.NEW_EDIT)
	public final void init(final EditPageModel model) {
		model.setArchive(BeanUtils.instantiateClass(ArchiveImpl.class));
	}
}
