package de.mq.archive.web.edit;


import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.apache.wicket.validation.validator.PatternValidator;


import de.mq.archive.domain.Category;
import de.mq.archive.domain.support.MongoFileRepository;
import de.mq.archive.web.ActionButton;
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.ComponentFactory;
import de.mq.archive.web.search.ArchiveModelParts;
import de.mq.archive.web.search.SearchPage;

public class EditPage extends WebPage {
	
	
	static final String FORM_NAME = "editForm";

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EditPageModelWeb editPageModelWeb;
	
	@Inject()
	private ComponentFactory componentFactory;
	
	@Inject()
	@Named("editActionListener")
	private ActionListener<String> actionListener;

	private Map<ArchiveModelParts, Class<? extends Component>> autoGeneratedFields = new HashMap<>();
	@Inject()
	private MongoFileRepository fileRepository;
	public EditPage(final PageParameters parameters) {
		
		
		autoGeneratedFields.put(ArchiveModelParts.Text, TextArea.class);
		autoGeneratedFields.put(ArchiveModelParts.DocumentDate, DateTextField.class);
		autoGeneratedFields.put(ArchiveModelParts.Name, RequiredTextField.class);
		autoGeneratedFields.put(ArchiveModelParts.ArchiveId, TextField.class);
		
	
		final Form<String> editForm = new Form<String>(FORM_NAME) {

			private static final long serialVersionUID = 1L;


			@Override
			protected void onError() {
				Arrays.stream(I18NEditPageMessagesParts.values()).forEach(part -> {
					((Component) get(part.wicketId())).setVisible(((FeedbackPanel) get(part.wicketIdFeedback())).anyMessage());
				});
				
			}
		
		};
		
		
		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(part -> addMessage(part, editForm) );
		
		add(editForm);
		
		Arrays.stream(I18NEditPageModelParts.values()).filter(value -> ! value.isWithInForm()).forEach(value -> add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), value, Label.class)));
		
		
		Arrays.stream(I18NEditPageModelParts.values()).filter(value ->  value.isWithInForm() && ! value.isButton()).forEach(value -> editForm.add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), value, Label.class)));
		Arrays.stream(I18NEditPageModelParts.values()).filter(value ->  value.isButton()).forEach(value -> editForm.add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), value, ActionButton.class)));
		
		button(editForm, I18NEditPageModelParts.SaveButton).addActionListener(EditPageController.SAVE_ACTION , actionListener);
		autoGeneratedFields.entrySet().stream().forEach(entry ->  editForm.add(componentFactory.newComponent(editPageModelWeb.getArchiveModelWeb(), entry.getKey(),entry.getValue())) );
		
		final DropDownChoice<?> categoryBox = (DropDownChoice<?>) componentFactory.newComponent(editPageModelWeb.getArchiveModelWeb(), ArchiveModelParts.Category,  DropDownChoice.class, new ListModel<>(Arrays.asList(Category.values()) ));
		editForm.add(categoryBox);
		categoryBox.setNullValid(true);
		
		Arrays.stream(I18NEditPageModelParts.values()).filter(value -> value.isButton()).forEach(value -> ((ActionButton<?>)editForm.get(value.wicketId())).addActionListener(id -> setResponsePage(SearchPage.class)));
		
		button(editForm, I18NEditPageModelParts.CancelButton).setDefaultFormProcessing(false);
		
		
		editPageModelWeb.getI18NLabels().intoWeb(getLocale());
		editPageModelWeb.getI18NMessages().intoWeb(getLocale());
		
		input(editForm, ArchiveModelParts.Name).add(new PatternValidator("[ 0-9a-zA-Z_-]{1,25}"));
		input(editForm, ArchiveModelParts.ArchiveId).add(new PatternValidator("[0-9a-zA-Z_-]{0,25}"));
		final  FileUploadField fileUpload = new FileUploadField("fileUpload");
		final Form<String> uploadForm = new Form<String>("uploadForm") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
			
				try {
					if(fileUpload.getFileUpload() == null ){
						return;
					}
					final InputStream is = fileUpload.getFileUpload().getInputStream();
					fileRepository.save(is, editPageModelWeb.getArchiveModelWeb().toDomain().id());
					System.out.println(fileUpload.getFileUpload().getContentType());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				super.onSubmit();
			}
			
			
			
		};
				
		uploadForm.add(fileUpload);
		add(uploadForm);
	
			
	
	}

	@SuppressWarnings("unchecked")
	private TextField<String> input(final Form<String> editForm, final ArchiveModelParts part) {
			return (TextField<String>) editForm.get(part.wicketId());
	}


	@SuppressWarnings("unchecked")
	private ActionButton<String> button(final Form<String> editForm, final I18NEditPageModelParts parts) {
		return (ActionButton<String>)editForm.get(parts.wicketId());
	}


	private void addMessage(final I18NEditPageMessagesParts part, final Form<?> form ){
		
		final Label messageLabel = componentFactory.newComponent(editPageModelWeb.getI18NMessages(), part, Label.class);
		messageLabel.setVisible(false);
		form.add(messageLabel);
		
		final FeedbackPanel panel = new FeedbackPanel(part.wicketIdFeedback(), message -> message.getReporter().equals(form.get(part.wicketIdInput())));
		
		
	   panel.setVisible(false);
		form.add(panel);
	}
	

	
	
	

}
