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

		add(newLabel(I18NSearchPageModelParts.SearchCriteriaHeadline));

		searchForm.add(newTextField(ArchiveModelParts.Name));
		searchForm.add(newLabel(I18NSearchPageModelParts.SearchNameLabel));

		final DropDownChoice<Category> dropDownChoice = new DropDownChoice<>(ArchiveModelParts.Category.wicketId(), searchPageModel.getSearchCriteriaWeb().part(ArchiveModelParts.Category, Category.class), Arrays.asList(Category.values()));
		dropDownChoice.setNullValid(true);
		searchForm.add(dropDownChoice);
		searchForm.add(newLabel(I18NSearchPageModelParts.SearchCategoryLabel));

		searchForm.add(newTextField(ArchiveModelParts.ArchiveId));
		searchForm.add(newLabel(I18NSearchPageModelParts.SearchArchiveLabel));

		final ActionButton<String> searchButton = newButton(I18NSearchPageModelParts.SearchButton);

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
		changeButton = newButton(I18NSearchPageModelParts.ChangeButton);
		showButton = newButton(I18NSearchPageModelParts.ShowButton);
		add(form);
		add(newLabel(I18NSearchPageModelParts.ApplicationHeadline));
		add(newLabel(I18NSearchPageModelParts.PageHeadline));
		form.add(group);
		group.add(newButton(I18NSearchPageModelParts.NewButton));
		group.add(changeButton);
		group.add(showButton);
		group.add(newLabel(I18NSearchPageModelParts.SearchTableHeadline));

		final ListView<Archive> persons = new ListView<Archive>("documents", searchPageModel.getArchivesWeb()) {

			private static final long serialVersionUID = 1L;

			protected void populateItem(final ListItem<Archive> item) {

				final EnumModel<Archive> currentRow = searchPageController.newWebModel(item.getModelObject());

				item.add(new Radio<String>("id", currentRow.part(ArchiveModelParts.Id, String.class)));

				item.add(newLabel(currentRow, ArchiveModelParts.Name));
				item.add(newLabel(currentRow, ArchiveModelParts.Category));
				item.add(newLabel(currentRow, ArchiveModelParts.DocumentDate));
				item.add(newLabel(currentRow, ArchiveModelParts.ArchiveId));

			}

			;
		};

		group.add(newLabel(I18NSearchPageModelParts.NameHeader));
		group.add(newLabel(I18NSearchPageModelParts.CategoryHeader));
		group.add(newLabel(I18NSearchPageModelParts.DateHeader));
		group.add(newLabel(I18NSearchPageModelParts.ArchiveIdHeader));
		group.add(persons);
		Session.get().setLocale(Locale.GERMAN);
		enableButtons();
		searchPageModel.getI18NLabels().intoWeb(getLocale());

	}

	private TextField<String> newTextField(final ArchiveModelParts part) {
		return new TextField<>(part.wicketId(), searchPageModel.getSearchCriteriaWeb().part(part, String.class));
	}

	private ActionButton<String> newButton(final I18NSearchPageModelParts part) {
		return new ActionButton<>(part.wicketId(), searchPageModel.getI18NLabels().part(part));
	}

	private Label newLabel(final I18NSearchPageModelParts part) {
		return new Label(part.wicketId(), searchPageModel.getI18NLabels().part(part));
	}

	private void enableButtons() {
		changeButton.setEnabled(searchPageModel.isSelected());
		showButton.setEnabled(searchPageModel.isSelected());
	}
	private Label newLabel(final EnumModel<Archive> currentRow, final ArchiveModelParts part) {
		return new Label(part.wicketId(), currentRow.part(part, String.class));
	}

}
