package de.mq.archive.web.search;



import java.util.Arrays;

import javax.inject.Inject;

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
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.context.MessageSource;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;
import de.mq.archive.domain.Category;


public class SearchPage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ArchiveService archiveService;
	
	@Inject
	private MessageSource messageSource;


	private final Model<String> searchNameLabelModel = new Model<>();
	private final Model<String> searchCategoryLabelModel = new Model<>();
	private final Model<String> searchArchiveLabelModel = new Model<>();
	
	private final Model<Category> searchCategoryModel = new Model<>();
	private final Model<String> searchArchiveModel = new Model<>();
	private final Model<String> searchCriteriaHeadlineModel = new Model<>();
	
	
	private final Model<String> nameHeaderLabelModel = new Model<>();
	private final Model<String> categoryHeaderLabelModel = new Model<>();
	private final Model<String> dateHeaderLabelModel = new Model<>();
	private final Model<String> archiveIdHeaderLabelModel = new Model<>();
	private final Model<String> newButtonModel = new Model<>();
	private final Model<String> changeButtonModel = new Model<>();
	private final Model<String> showButtonModel = new Model<>();
	private final Model<String> searchButtonModel = new Model<>();
	private final Model<String> searchTableHeadlineModel = new Model<>();
	
	private final Model<String> applicationHeadlineModel = new Model<>();
	private final Model<String> pageHeadlineModel = new Model<>();
	
	private final Model<String> nameSearchModel = new Model<>(); 
	
	public SearchPage(final PageParameters parameters) {
		
		
		
		final Form<String> searchForm = new Form<>("searchForm");
		
		add(searchForm);
		add(new Label("searchCriteriaHeadline", searchCriteriaHeadlineModel));
	
		searchForm.add(new TextField<String>("searchName", nameSearchModel));
		searchForm.add(new Label("searchNameLabel" , searchNameLabelModel));
		
		
		
		final DropDownChoice<Category> dropDownChoice = new DropDownChoice<>("searchCategrory", searchCategoryModel, Arrays.asList(Category.values()));
		dropDownChoice.setNullValid(true);
	   searchForm.add(dropDownChoice);
		searchForm.add(new Label("searchCategoryLabel" , searchCategoryLabelModel));
		
		searchForm.add(new TextField<String>("searchArchive", searchArchiveModel));
		searchForm.add(new Label("searchArchiveLabel" , searchArchiveLabelModel));
		
		
		searchForm.add(new Button("searchButton", searchButtonModel) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				System.out.println(nameSearchModel.getObject());
				System.out.println(searchCategoryModel.getObject());
				System.out.println(searchArchiveModel.getObject());
				super.onSubmit();
			}
			
			
		});
		
		final RadioGroup<Archive> group=new RadioGroup<Archive>("group", new Model<Archive>());
		final Form<Archive> form = new Form<Archive>("form");
		final Button newButton = new Button("newButton", newButtonModel);
		final Button changeButton = new Button("changeButton", changeButtonModel);		
		final Button showButton = new Button("showButton", showButtonModel);				
		add(form);
		add(new Label("applicationHeadline", applicationHeadlineModel));
		add(new Label("pageHeadline", pageHeadlineModel));
		form.add(group);
		group.add(newButton);
		group.add(changeButton);
		group.add(showButton);
		group.add(new Label("searchTableHeadline", searchTableHeadlineModel));
		
		
		   final ListView<Archive> persons = new ListView<Archive>("documents", archiveService.archives(null)) {
		  
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem<Archive> item) {
		      item.add(new Radio<Archive>("document", item.getModel() ));
		      item.add(new Label("name", item.getModelObject().name()));
		      item.add(new Label("category", item.getModelObject().category() ));
		      item.add(new Label("documentDate", item.getModelObject().documentDate() ));
		      item.add(new Label("archiveId", item.getModelObject().archiveId() ));
		 
		    };
		};

	
		group.add(new Label("nameHeader", nameHeaderLabelModel));
		group.add(new Label("categoryHeader", categoryHeaderLabelModel));
		group.add(new Label("dateHeader", dateHeaderLabelModel));
		group.add(new Label("archiveIdHeader", archiveIdHeaderLabelModel));
		group.add(persons);
    }
	
	
	
	
	



	@Override
	protected void onBeforeRender() {
		
		searchCriteriaHeadlineModel.setObject(string("archive_search_search_headline"));
		searchNameLabelModel.setObject(string("archive_search_search_name"));
		
		searchCategoryLabelModel.setObject(string("archive_search_search_category"));
		
		searchArchiveLabelModel.setObject(string("archive_search_search_archive"));
		
		searchButtonModel.setObject(string("archive_search_search_button"));
		
		nameHeaderLabelModel.setObject(string("archive_search_header_name"));
		
		categoryHeaderLabelModel.setObject(string("archive_search_header_category"));
		dateHeaderLabelModel.setObject(string("archive_search_header_date"));
		archiveIdHeaderLabelModel.setObject(string("archive_search_header_archive"));
		
		
		newButtonModel.setObject(string("archive_search_button_new"));
		changeButtonModel.setObject(string("archive_search_button_change"));
		showButtonModel.setObject(string("archive_search_button_show"));
		
		searchTableHeadlineModel.setObject(string("archive_search_table_headline"));
		
		applicationHeadlineModel.setObject(string("archive_headline"));
		pageHeadlineModel.setObject(string("archive_search_headline"));
		super.onBeforeRender();
	}








	private String string(final String key){
		return messageSource.getMessage(key, null, getLocale());
	}
}
