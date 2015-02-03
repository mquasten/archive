package de.mq.archive.web.edit;

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
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.mq.archive.domain.Category;
import de.mq.archive.web.ActionButton;
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.ComponentFactory;
import de.mq.archive.web.search.ArchiveModelParts;
import de.mq.archive.web.search.SearchPage;

public class EditPage extends WebPage {
	
	
	private static final String FORM_NAME = "editForm";

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EditPageModelWeb editPageModelWeb;
	
	@Inject()
	private ComponentFactory componentFactory;
	
	@Inject()
	@Named("editActionListener")
	private ActionListener<String> actionListener;

	private Map<ArchiveModelParts, Class<? extends Component>> autoGeneratedFields = new HashMap<>();
	
	
	@SuppressWarnings("unchecked")
	public EditPage(final PageParameters parameters) {
		
		autoGeneratedFields.put(ArchiveModelParts.Text, TextArea.class);
		autoGeneratedFields.put(ArchiveModelParts.DocumentDate, DateTextField.class);
		autoGeneratedFields.put(ArchiveModelParts.Name, TextField.class);
		autoGeneratedFields.put(ArchiveModelParts.ArchiveId, TextField.class);
		
	
		final Form<String> editForm = new Form<String>(FORM_NAME) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError() {
				
				
				
			}
		};
		
		
		
		Arrays.stream(I18NEditPageMessagesParts.values()).forEach(part -> addMessage(part, editForm) );
		
	
	
		add(editForm);
		
		
		Arrays.stream(I18NEditPageModelParts.values()).filter(value -> ! value.isWithInForm()).forEach(value -> add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), value, Label.class)));
		
		
		
		Arrays.stream(I18NEditPageModelParts.values()).filter(value ->  value.isWithInForm() && ! value.isButton()).forEach(value -> editForm.add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), value, Label.class)));
		Arrays.stream(I18NEditPageModelParts.values()).filter(value ->  value.isButton()).forEach(value -> editForm.add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), value, ActionButton.class)));
		
		((ActionButton<String>)editForm.get(I18NEditPageModelParts.SaveButton.wicketId())).addActionListener("save" , actionListener);
		autoGeneratedFields.entrySet().stream().forEach(entry ->  editForm.add(componentFactory.newComponent(editPageModelWeb.getArchiveModelWeb(), entry.getKey(),entry.getValue())) );
		
		final DropDownChoice<?> categoryBox = (DropDownChoice<?>) componentFactory.newComponent(editPageModelWeb.getArchiveModelWeb(), ArchiveModelParts.Category,  DropDownChoice.class, new ListModel<>(Arrays.asList(Category.values()) ));
		editForm.add(categoryBox);
		categoryBox.setNullValid(true);
		
		Arrays.stream(I18NEditPageModelParts.values()).filter(value -> value.isButton()).forEach(value -> ((ActionButton<?>)editForm.get(value.wicketId())).addActionListener(id -> setResponsePage(SearchPage.class)));
		
		((ActionButton<String>)editForm.get(I18NEditPageModelParts.CancelButton.wicketId())).setDefaultFormProcessing(false);
		
		
		editPageModelWeb.getI18NLabels().intoWeb(getLocale());
		editPageModelWeb.getI18NMessages().intoWeb(getLocale());
		
		
		
	
	}


	private void addMessage(final I18NEditPageMessagesParts part, final Form<?> form ){
		final Label messageLabel = componentFactory.newComponent(editPageModelWeb.getI18NMessages(), part, Label.class);
		messageLabel.setVisible(false);
		form.add(messageLabel);
		FeedbackPanel panel = new FeedbackPanel(part.wicketIdFeedback());
		panel.setVisible(false);
		form.add(panel);
	}
	

	
	
	

}
