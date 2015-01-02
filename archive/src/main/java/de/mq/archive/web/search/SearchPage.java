package de.mq.archive.web.search;



import java.util.Arrays;
import java.util.List;


import java.util.Locale;

import javax.inject.Inject;

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
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.Category;




public class SearchPage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	@Inject()
	private SearchPageController serachPageController;
	
	
	@Inject()
	private ArchiveModel searchcriteria; 

	@Inject()
	private I18NSearchPageModel labelModel;

	
	 private final IModel<List<Archive>> archives = new ListModel<>();
	 
	 private final IModel<String> selectedArchive = new Model<>();
	 
	 
	 private final Button changeButton;
	 private final Button showButton;
	

	public SearchPage(final PageParameters parameters) {
		
		
		
		final Form<String> searchForm = new Form<>("searchForm");
		
		add(searchForm);
		add(new Label("searchCriteriaHeadline", labelModel.part(I18NSearchPageModelParts.SearchCriteriaHeadline)));
	
		searchForm.add(new TextField<>("searchName", searchcriteria.part(ArchiveModelParts.Name, String.class)));
		searchForm.add(new Label("searchNameLabel" ,  labelModel.part(I18NSearchPageModelParts.SearchNameLabel)));
		
		
		
		final DropDownChoice<Category> dropDownChoice = new DropDownChoice<>("searchCategrory",    searchcriteria.part(ArchiveModelParts.Category, Category.class), Arrays.asList(Category.values()));
		dropDownChoice.setNullValid(true);
	   searchForm.add(dropDownChoice);
		searchForm.add(new Label("searchCategoryLabel" , labelModel.part(I18NSearchPageModelParts.SearchCategoryLabel)));
		
		searchForm.add(new TextField<>("searchArchive",  searchcriteria.part(ArchiveModelParts.ArchiveId, String.class)));
		searchForm.add(new Label("searchArchiveLabel" , labelModel.part(I18NSearchPageModelParts.SearchArchiveLabel)));
		
		
		searchForm.add(new Button("searchButton", labelModel.part(I18NSearchPageModelParts.SearchButton)) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
			
				archives.setObject(serachPageController.archives(searchcriteria.toDomain()));
				super.onSubmit();
			}
			
			
		});
		
	
		final RadioGroup<String> group=new RadioGroup<String>("group", selectedArchive);
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
		final Button newButton = new Button("newButton", labelModel.part(I18NSearchPageModelParts.NewButton));
		changeButton = new Button("changeButton", labelModel.part(I18NSearchPageModelParts.ChangeButton)) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				System.out.println(">>>>" + group.getModel().getObject());
				super.onSubmit();
			}

		
			
			
			
		};
		showButton = new Button("showButton", labelModel.part(I18NSearchPageModelParts.ShowButton));				
		add(form);
		add(new Label("applicationHeadline", labelModel.part(I18NSearchPageModelParts.ApplicationHeadline)));
		add(new Label("pageHeadline", labelModel.part(I18NSearchPageModelParts.PageHeadline)));
		form.add(group);
		group.add(newButton);
		group.add(changeButton);
		group.add(showButton);
		group.add(new Label("searchTableHeadline", labelModel.part(I18NSearchPageModelParts.SearchTableHeadline)));
		
		
		   final ListView<Archive> persons = new ListView<Archive>("documents", archives) {
		  
		   
			private static final long serialVersionUID = 1L;

			protected void populateItem(final ListItem<Archive> item) {
				
				final  ArchiveModel currentRow = new  ArchiveModelImpl();
				currentRow.intoWeb(item.getModelObject());
				item.add(new Radio<String>("document", currentRow.part(ArchiveModelParts.Id, String.class)));
				
		      item.add(new Label("name", currentRow.part(ArchiveModelParts.Name, String.class)));
		      item.add(new Label("category",currentRow.part(ArchiveModelParts.Category,String.class )));
		      item.add(new Label("documentDate",currentRow.part(ArchiveModelParts.DocumentDate,String.class )));
		      item.add(new Label("archiveId", currentRow.part(ArchiveModelParts.ArchiveId,String.class)));
		 
		    };
		};

	
		group.add(new Label("nameHeader", labelModel.part(I18NSearchPageModelParts.NameHeaderLabel)));
		group.add(new Label("categoryHeader", labelModel.part(I18NSearchPageModelParts.CategoryHeaderLabel)));
		group.add(new Label("dateHeader", labelModel.part(I18NSearchPageModelParts.DateHeaderLabel)));
		group.add(new Label("archiveIdHeader", labelModel.part(I18NSearchPageModelParts.ArchiveIdHeaderLabel)));
		group.add(persons);
		//Session.get().setLocale(Locale.ENGLISH);
		enableButtons();
		labelModel.intoWeb(getLocale());
		
    }
	
	







	private void enableButtons() {
		changeButton.setEnabled(selectedArchive.getObject()!=null);
		showButton.setEnabled(selectedArchive.getObject()!=null);
	}







	
}
