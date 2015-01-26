package de.mq.archive.web.edit;

import java.util.Arrays;
import java.util.Date;

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
import de.mq.archive.web.search.SearchPage;

public class EditPage extends WebPage {
	
	
	private static final long serialVersionUID = 1L;

	public EditPage(final PageParameters parameters) {
		final Form<String> editForm = new Form<>("editForm");

		add(editForm);
		add(new Label("applicationHeadline", new Model<>("ApplicationHeadLine")));
		add(new Label("pageHeadline", new Model<>("PageHeadline")));
		
		editForm.add(new Label("nameLabel" , new Model<>("Name")));
		editForm.add(new TextField<>("name" , new Model<>()));
		
		editForm.add(new Label("categoryLabel" , new Model<>("Category")));
		editForm.add(new DropDownChoice<Category>("category" , new Model<>(), Arrays.asList(Category.values())));
		
		editForm.add(new Label("archiveIdLabel" , new Model<>("ArchiveId")));
		editForm.add(new TextField<>("archiveId" , new Model<>()));
	
		editForm.add(new Label("documentDateLabel" , new Model<>("Datum")));
		final DateTextField dateTextField = new DateTextField ("documentDate" , new Model<>(new Date()));
   
		
		editForm.add(dateTextField);
		
		editForm.add(new Label("textLabel" , new Model<>("Text")));
		editForm.add(new TextArea<>("text" , new Model<>()));
		
		final ActionButton<Object> cancelButton = new ActionButton<>("cancelButton" , new Model<>("abbrechen"));
		cancelButton.addActionListener(id -> setResponsePage(SearchPage.class));
		editForm.add(cancelButton);
		editForm.add(new ActionButton<>("saveButton" , new Model<>("speichern")));
	}

}
