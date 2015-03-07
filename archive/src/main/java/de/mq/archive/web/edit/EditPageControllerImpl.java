package de.mq.archive.web.edit;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Named;












import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
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
	@Override
	public final void save(final EditPageModel model) {
		archiveService.save(model.getArchive());
		
	}
	
	@Named(SearchPageModel.INIT_EDIT)
	@Override
	public final void init(final SearchPageModel searchPageModel, final EditPageModel model) {
		model.setArchive(archiveService.archive(searchPageModel.getSelectedArchiveId()));
	}
	
	@Named(SearchPageModel.NEW_EDIT)
	@Override
	public final void init(final EditPageModel model) {
		model.setArchive(BeanUtils.instantiateClass(ArchiveImpl.class));
	}
	
	@Named(EditPageModel.UPLOAD_ACTION)
	@Override
	public final void uplod(final EditPageModel model, final FileUploadField fileUploadField) {
		
		
		final FileUpload fileUpload = fileUploadField.getFileUpload();
		if(fileUpload == null ){
			return;
		}
		
		try(final InputStream is = fileUpload.getInputStream() ) {
			
			archiveService.upload(model.getArchive(), is, fileUpload.getClientFileName(),  fileUpload.getContentType());
		} catch (final IOException ex) {
			throw new IllegalStateException(ex);
		}
	}	
		
		

}
