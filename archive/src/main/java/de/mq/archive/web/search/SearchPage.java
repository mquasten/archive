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
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;
import de.mq.archive.web.ActionButton;
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.EnumModel;

public class SearchPage extends WebPage {
	private static final long serialVersionUID = 1L;

	@Inject
	private SearchPageController searchPageController;

	@Inject()
	private SearchPageModelWeb searchPageModel;

	@Inject()
	@Named("searchActionListener")
	private ActionListener<String> actionListener;

	private final Button changeButton;
	private final Button showButton;

	public SearchPage(final PageParameters parameters) {
		final Form<String> searchForm = new Form<>("searchForm");

		add(searchForm);

		add(new Label("searchCriteriaHeadline", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.SearchCriteriaHeadline)));

		searchForm.add(new TextField<>("searchName", searchPageModel.getSearchCriteriaWeb().part(ArchiveModelParts.Name, String.class)));
		searchForm.add(new Label("searchNameLabel", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.SearchNameLabel)));

		final DropDownChoice<Category> dropDownChoice = new DropDownChoice<>("searchCategrory", searchPageModel.getSearchCriteriaWeb().part(ArchiveModelParts.Category, Category.class), Arrays.asList(Category.values()));
		dropDownChoice.setNullValid(true);
		searchForm.add(dropDownChoice);
		searchForm.add(new Label("searchCategoryLabel", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.SearchCategoryLabel)));

		searchForm.add(new TextField<>("searchArchive", searchPageModel.getSearchCriteriaWeb().part(ArchiveModelParts.ArchiveId, String.class)));
		searchForm.add(new Label("searchArchiveLabel", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.SearchArchiveLabel)));

		final ActionButton<String> searchButton = new ActionButton<>("searchButton", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.SearchButton));

		searchButton.addActionListener(SearchPageController.SEARCH_ACTION, actionListener);

		searchButton.addActionListener(action -> enableButtons());
		searchForm.add((Component) searchButton);

		final RadioGroup<String> group = new RadioGroup<String>("group", searchPageModel.getSelectedArchiveWeb());
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
		final Button newButton = new Button("newButton", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.NewButton));
		changeButton = new Button("changeButton", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.ChangeButton));
		showButton = new Button("showButton", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.ShowButton));
		add(form);
		add(new Label("applicationHeadline", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.ApplicationHeadline)));
		add(new Label("pageHeadline", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.PageHeadline)));
		form.add(group);
		group.add(newButton);
		group.add(changeButton);
		group.add(showButton);
		group.add(new Label("searchTableHeadline", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.SearchTableHeadline)));

		final ListView<Archive> persons = new ListView<Archive>("documents", searchPageModel.getArchivesWeb()) {

			private static final long serialVersionUID = 1L;

			protected void populateItem(final ListItem<Archive> item) {

				final EnumModel<Archive> currentRow = searchPageController.newWebModel(item.getModelObject());

				item.add(new Radio<String>("id", currentRow.part(ArchiveModelParts.Id, String.class)));

				item.add(new Label("name", currentRow.part(ArchiveModelParts.Name, String.class)));
				item.add(new Label("category", currentRow.part(ArchiveModelParts.Category, String.class)));
				item.add(new Label("documentDate", currentRow.part(ArchiveModelParts.DocumentDate, String.class)));
				item.add(new Label("archiveId", currentRow.part(ArchiveModelParts.ArchiveId, String.class)));

			};
		};

		group.add(new Label("nameHeader", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.NameHeader)));
		group.add(new Label("categoryHeader", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.CategoryHeader)));
		group.add(new Label("dateHeader", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.DateHeader)));
		group.add(new Label("archiveIdHeader", searchPageModel.getI18NLabels().part(I18NSearchPageModelParts.ArchiveIdHeader)));
		group.add(persons);
		Session.get().setLocale(Locale.ENGLISH);
		enableButtons();
		searchPageModel.getI18NLabels().intoWeb(getLocale());

	}

	private void enableButtons() {
		changeButton.setEnabled(searchPageModel.isSelected());
		showButton.setEnabled(searchPageModel.isSelected());
	}

}
