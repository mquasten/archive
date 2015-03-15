package de.mq.archive.web.edit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map.Entry;

import javax.inject.Named;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.GridFsInfo;
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
	
	@Named(EditPageModel.DELETE_ACTION)
	@Override
	public final void delete(final EditPageModel model) {
		
		if( ! model.isPersistent() ) {
		    return;
		}
		
		archiveService.delte(model.getArchive());
		
	}
	
	@Named(SearchPageModel.INIT_EDIT)
	@Override
	public final void init(final SearchPageModel searchPageModel, final EditPageModel model) {
		model.setEditable(true);
		assignArchive(archiveService.archive(searchPageModel.getSelectedArchiveId()), model);
		
	}
	
	@Named(SearchPageModel.INIT_READONLY)
	@Override
	public final void initReadOnly(final SearchPageModel searchPageModel, final EditPageModel model) {
		model.setEditable(false);
		assignArchive(archiveService.archive(searchPageModel.getSelectedArchiveId()), model);
		
	}
	
	@Named(SearchPageModel.NEW_EDIT)
	@Override
	public final void init(final EditPageModel model) {
		assignArchive(BeanUtils.instantiateClass(ArchiveImpl.class), model);
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
			assignArchive(model.getArchive(), model);
		} catch (final IOException ex) {
			throw new IllegalStateException(ex);
		}
	}	
	@Named(EditPageModel.DELETE_UPLOAD_ACTION)
	@Override
	public final void deleteUpload(final EditPageModel model) {
	
		if( ! StringUtils.hasText(model.getSelectedAttachementId())){
			return;
		}
		
		archiveService.deleteAttachement(model.getSelectedAttachementId());
		assignArchive(model.getArchive(), model);
	}
	
	@Named(EditPageModel.SHOW_ATTACHEMENT_ACTION)
	@Override
	public final void showAttachement(final EditPageModel model) {
		if( ! StringUtils.hasText(model.getSelectedAttachementId())){
			return;
		}
		final Entry<GridFsInfo<String>, byte[]> entry = archiveService.content(model.getSelectedAttachementId());
		final AbstractResourceStreamWriter rstream = new AbstractResourceStreamWriter() {
			 

			private static final long serialVersionUID = 1L;

			@Override
         public void write(OutputStream output) throws IOException {
             output.write(entry.getValue());
         }
     };

     final ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(rstream, entry.getKey().filename());        
   
     RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);
	}



	private void assignArchive(final Archive archive, final EditPageModel model) {
		model.setArchive(archive);
		archiveService.attachements(archive).forEach(attachement -> model.add(attachement));
	}
		
		

}
