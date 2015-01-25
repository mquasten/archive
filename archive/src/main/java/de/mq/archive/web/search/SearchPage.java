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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.beans.BeanUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;
import de.mq.archive.web.ActionButton;
import de.mq.archive.web.ActionListener;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;

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

	private final Button changeButton;
	private final Button showButton;

	public SearchPage(final PageParameters parameters) {
		final Form<String> searchForm = new Form<>("searchForm");

		add(searchForm);

		add(newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchCriteriaHeadline, Label.class));

		searchForm.add(newComponent(searchPageModel.getSearchCriteriaWeb() , ArchiveModelParts.Name, TextField.class));
		searchForm.add(newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchNameLabel, Label.class));

		
		@SuppressWarnings("unchecked")
		final DropDownChoice<Category> dropDownChoice = newComponent(searchPageModel.getSearchCriteriaWeb(), ArchiveModelParts.Category, DropDownChoice.class, new ListModel<>(Arrays.asList(Category.values())));
		
		dropDownChoice.setNullValid(true);
		searchForm.add(dropDownChoice);
		searchForm.add(newComponent( searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchCategoryLabel, Label.class));

		searchForm.add(newComponent(searchPageModel.getSearchCriteriaWeb(), ArchiveModelParts.ArchiveId, TextField.class));
		searchForm.add(newComponent( searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchArchiveLabel, Label.class	));

		@SuppressWarnings("unchecked")
		final ActionButton<String> searchButton = newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchButton, ActionButton.class);

		searchButton.addActionListener(SearchPageController.SEARCH_ACTION, actionListener);

		searchButton.addActionListener(action -> enableButtons());
		searchForm.add((Component) searchButton);

		@SuppressWarnings("unchecked")
		final RadioGroup<String> group = newComponent(WICKET_ID_GROUP, searchPageModel.getSelectedArchiveWeb(), RadioGroup.class);
				
				
	
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
		changeButton = newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.ChangeButton, ActionButton.class);
		showButton = newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.ShowButton,  ActionButton.class);
		add(form);
		add(newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.ApplicationHeadline, Label.class));
		add(newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.PageHeadline, Label.class));
		form.add(group);
		group.add(newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.NewButton, ActionButton.class));
		group.add(changeButton);
		group.add(showButton);
		group.add(newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.SearchTableHeadline, Label.class));

		final ListView<Archive> persons = new ListView<Archive>("documents", searchPageModel.getArchivesWeb()) {

			private static final long serialVersionUID = 1L;

			protected void populateItem(final ListItem<Archive> item) {

				final TwoWayMapping<Archive, Enum<?>> currentRow = searchPageController.newWebModel(item.getModelObject());

				item.add(newComponent(currentRow, ArchiveModelParts.Id, Radio.class));

				item.add(newComponent(currentRow, ArchiveModelParts.Name, Label.class));
				item.add(newComponent(currentRow, ArchiveModelParts.Category, Label.class));
				item.add(newComponent(currentRow, ArchiveModelParts.DocumentDate, Label.class));
				item.add(newComponent(currentRow, ArchiveModelParts.ArchiveId, Label.class));

			}

			;
		};

		group.add(newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.NameHeader, Label.class));
		group.add(newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.CategoryHeader, Label.class));
		group.add(newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.DateHeader, Label.class));
		group.add(newComponent(searchPageModel.getI18NLabels(), I18NSearchPageModelParts.ArchiveIdHeader, Label.class));
		group.add(persons);
		Session.get().setLocale(Locale.GERMAN);
		enableButtons();
		searchPageModel.getI18NLabels().intoWeb(getLocale());

	}

	private <T> T newComponent(final OneWayMapping<?,Enum<?> > model, final Enum<?> part, final Class<T> clazz) {
	
	   try {
	  
			return BeanUtils.instantiateClass(clazz.getConstructor(String.class, IModel.class), ((WicketIdAware)part).wicketId(), model.part(part) );
	
	   } catch (final Exception  ex) {
			throw new IllegalStateException("Unable to create Component", ex);
		}
		
	}
	
	private <T> T newComponent(final OneWayMapping<?,Enum<?> > model, final Enum<?> part, final Class<T> clazz , final IModel<?>valueModel ) {
		 try {
			  
				return BeanUtils.instantiateClass(clazz.getConstructor(String.class, IModel.class, IModel.class), ((WicketIdAware)part).wicketId(), model.part(part), valueModel );
		
		   } catch (final Exception  ex) {
				throw new IllegalStateException("Unable to create Component", ex);
			}
	}

	private <T> T newComponent(String wicketId, final IModel<?> model, final Class<T> clazz) {
		try {
			return BeanUtils.instantiateClass(clazz.getConstructor(String.class, IModel.class), wicketId,  model );
		} catch (final Exception ex) {
			throw new IllegalStateException("Unable to create Component", ex);
		}
	}
	

	
	private void enableButtons() {
		changeButton.setEnabled(searchPageModel.isSelected());
		showButton.setEnabled(searchPageModel.isSelected());
	}
	

}
