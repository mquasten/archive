package de.mq.archive.web.edit;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.mq.archive.domain.Category;
import de.mq.archive.web.ActionButton;
import de.mq.archive.web.ComponentFactory;
import de.mq.archive.web.search.SearchPage;

public class EditPage extends WebPage {
	
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EditPageModelWeb editPageModelWeb;
	
	@Inject()
	private ComponentFactory componentFactory;

	public EditPage(final PageParameters parameters) {
		
		final Form<String> editForm = new Form<>("editForm");

		
		add(editForm);
		add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), I18NEditPageModelParts.ApplicationHeadline, Label.class));
		add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), I18NEditPageModelParts.PageHeadline, Label.class));
		
		editForm.add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), I18NEditPageModelParts.NameLabel,  Label.class));
		editForm.add(new TextField<>("name" , new Model<>()));
		
		editForm.add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), I18NEditPageModelParts.CategoryLabel, Label.class));
		editForm.add(new DropDownChoice<Category>("category" , new Model<>(), Arrays.asList(Category.values())));
		
		editForm.add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), I18NEditPageModelParts.ArchiveIdLabel, Label.class));
		editForm.add(new TextField<>("archiveId" , new Model<>()));
	
		editForm.add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), I18NEditPageModelParts.DocumentDateLabel, Label.class));
		final DateTextField dateTextField = new DateTextField ("documentDate" , new Model<>(new Date()));
   
	
		editForm.add(dateTextField);
		
		editForm.add(componentFactory.newComponent(editPageModelWeb.getI18NLabels(), I18NEditPageModelParts.TextLabel, Label.class));
		editForm.add(new TextArea<>("text" , new Model<>()));
		
		final ActionButton<?> cancelButton = componentFactory.newComponent(editPageModelWeb.getI18NLabels(), I18NEditPageModelParts.CancelButton, ActionButton.class);
		cancelButton.addActionListener(id -> setResponsePage(SearchPage.class));
		editForm.add(cancelButton);
		final ActionButton<?> saveButton = componentFactory.newComponent(editPageModelWeb.getI18NLabels(), I18NEditPageModelParts.SaveButton, ActionButton.class);
		editForm.add(saveButton);
		
		editPageModelWeb.getI18NLabels().intoWeb(getLocale());
	}

}
