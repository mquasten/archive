package de.mq.archive.web.search;

import java.util.Arrays;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;
import de.mq.archive.web.ActionButton;
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.ComponentFactory;
import de.mq.archive.web.TwoWayMapping;
import de.mq.archive.web.edit.EditPage;

public class SearchPage extends WebPage {
	private static final String WICKET_ID_GROUP = "group";

	private static final long serialVersionUID = 1L;

	@Inject
	private SearchPageController searchPageController;

	@Inject()
	private SearchPageModelWeb searchPageModel;

	@Inject()
	@Named("searchActionListener")
	private ActionListener<String> actionListener;
	
	@Inject()
	@Named("editActionListener")
	private ActionListener<String> editActionListener;

	
	@Inject()
	private ComponentFactory componentFactory;
	
	private final ActionButton<String> changeButton;
	private final ActionButton<String>  showButton;

	
	@SuppressWarnings("unchecked")
	public SearchPage(final PageParameters parameters) {
		final Form<String> searchForm = new Form<>("searchForm");

		add(searchForm);

		add(componentFactory.newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchCriteriaHeadline, Label.class));

		searchForm.add(componentFactory.newComponent(searchPageModel.getSearchCriteriaWeb() , ArchiveModelParts.Name, TextField.class));
		searchForm.add(componentFactory.newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchNameLabel, Label.class));

		
		final DropDownChoice<Category> dropDownChoice = componentFactory.newComponent(searchPageModel.getSearchCriteriaWeb(), ArchiveModelParts.Category, DropDownChoice.class, new ListModel<>(Arrays.asList(Category.values())));
		
		dropDownChoice.setNullValid(true);
		searchForm.add(dropDownChoice);
		searchForm.add(componentFactory.newComponent( searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchCategoryLabel, Label.class));

		searchForm.add(componentFactory.newComponent(searchPageModel.getSearchCriteriaWeb(), ArchiveModelParts.ArchiveId, TextField.class));
		searchForm.add(componentFactory.newComponent( searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchArchiveLabel, Label.class	));

	
		final ActionButton<String> searchButton = componentFactory.newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchButton, ActionButton.class);

		searchButton.addActionListener(SearchPageController.SEARCH_ACTION, actionListener);
		
		
		
		

		searchButton.addActionListener(action -> enableButtons());
		searchForm.add((Component) searchButton);

	
		final RadioGroup<String> group = componentFactory.newComponent(WICKET_ID_GROUP, searchPageModel.getSelectedArchiveWeb(), RadioGroup.class);
				
				
	
		group.add(new AjaxFormChoiceComponentUpdatingBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
			
				enableButtons();
				target.add(changeButton);
				target.add(showButton);

			}

		});

		final Form<Archive> form = new Form<Archive>("form");
	
		changeButton = newButton(I18NSearchPageModelParts.ChangeButton);
		changeButton.addActionListener(SearchPageModel.INIT_EDIT, editActionListener );
		
		changeButton.addActionListener( action ->  setResponsePage(EditPage.class));
		
		showButton = componentFactory.newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.ShowButton,  ActionButton.class);
		
		showButton.addActionListener(SearchPageModel.INIT_READONLY,editActionListener);
		showButton.addActionListener( action ->  setResponsePage(EditPage.class));
		add(form);
		add(componentFactory.newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.ApplicationHeadline, Label.class));
		add(componentFactory.newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.PageHeadline, Label.class));
		form.add(group);
		final ActionButton<String> newButton = newButton(I18NSearchPageModelParts.NewButton);
		newButton.addActionListener(SearchPageModel.NEW_EDIT, editActionListener);		
				
		
		
		newButton.addActionListener(id -> setResponsePage(EditPage.class));
		group.add(newButton);
		group.add(changeButton);
		group.add(showButton);
		group.add(componentFactory.newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchTableHeadline, Label.class));

		final ListView<Archive> persons = new ListView<Archive>("documents", searchPageModel.getArchivesWeb()) {

			private static final long serialVersionUID = 1L;

			protected void populateItem(final ListItem<Archive> item) {

				final TwoWayMapping<Archive, Enum<?>> currentRow = searchPageController.newWebModel(item.getModelObject());

				item.add(componentFactory.newComponent(currentRow, ArchiveModelParts.Id, Radio.class));

				item.add(componentFactory.newComponent(currentRow, ArchiveModelParts.Name, Label.class));
				item.add(componentFactory.newComponent(currentRow, ArchiveModelParts.Category, Label.class));
				item.add(componentFactory.newComponent(currentRow, ArchiveModelParts.DocumentDate, Label.class));
				item.add(componentFactory.newComponent(currentRow, ArchiveModelParts.ArchiveId, Label.class));

			}

			;
		};

		group.add(componentFactory.newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.NameHeader, Label.class));
		group.add(componentFactory.newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.CategoryHeader, Label.class));
		group.add(componentFactory.newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.DateHeader, Label.class));
		group.add(componentFactory.newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.ArchiveIdHeader, Label.class));
		group.add(persons);
		Session.get().setLocale(Locale.GERMAN);
		enableButtons();
		searchPageModel.getI18NLabels().intoWeb(getLocale());

	}




	@SuppressWarnings("unchecked")
	private ActionButton<String> newButton(I18NSearchPageModelParts part) {
		return (ActionButton<String>) componentFactory.newComponent(searchPageModel.getI18NLabels(), part, ActionButton.class);
	}

	

	
	private void enableButtons() {
		changeButton.setEnabled(searchPageModel.isSelected());
		showButton.setEnabled(searchPageModel.isSelected());
	}
	

}
