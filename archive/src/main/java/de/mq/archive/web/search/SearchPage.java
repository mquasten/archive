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

	private final EnumModel<Archive> archiveModel = new ArchiveModelImpl();
	
	private OneWayStringMapping<MessageSource, Enum<?>> labelModel = new I18NSearchPageModelImpl();

	
	
	
	
	public SearchPage(final PageParameters parameters) {
		
		
		
		final Form<String> searchForm = new Form<>("searchForm");
		
		add(searchForm);
		add(new Label("searchCriteriaHeadline", labelModel.part(I18NSearchPageModelImpl.Parts.SearchCriteriaHeadline)));
	
		searchForm.add(new TextField<>("searchName", archiveModel.part(ArchiveModelImpl.Parts.Name, String.class)));
		searchForm.add(new Label("searchNameLabel" ,  labelModel.part(I18NSearchPageModelImpl.Parts.SearchNameLabel)));
		
		
		
		final DropDownChoice<Category> dropDownChoice = new DropDownChoice<>("searchCategrory",    archiveModel.part(ArchiveModelImpl.Parts.Category, Category.class), Arrays.asList(Category.values()));
		dropDownChoice.setNullValid(true);
	   searchForm.add(dropDownChoice);
		searchForm.add(new Label("searchCategoryLabel" , labelModel.part(I18NSearchPageModelImpl.Parts.SearchCategoryLabel)));
		
		searchForm.add(new TextField<>("searchArchive",  archiveModel.part(ArchiveModelImpl.Parts.ArchiveId, String.class)));
		searchForm.add(new Label("searchArchiveLabel" , labelModel.part(I18NSearchPageModelImpl.Parts.SearchArchiveLabel)));
		
		
		searchForm.add(new Button("searchButton", labelModel.part(I18NSearchPageModelImpl.Parts.SearchButton)) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				final Archive search = archiveModel.toDomain();
				System.out.println(search.name());
				System.out.println(search.category());
				System.out.println(search.archiveId());
				System.out.println(search.id());
				super.onSubmit();
			}
			
			
		});
		
		final RadioGroup<Archive> group=new RadioGroup<Archive>("group", new Model<Archive>());
		final Form<Archive> form = new Form<Archive>("form");
		final Button newButton = new Button("newButton", labelModel.part(I18NSearchPageModelImpl.Parts.NewButton));
		final Button changeButton = new Button("changeButton", labelModel.part(I18NSearchPageModelImpl.Parts.ChangeButton));		
		final Button showButton = new Button("showButton", labelModel.part(I18NSearchPageModelImpl.Parts.ShowButton));				
		add(form);
		add(new Label("applicationHeadline", labelModel.part(I18NSearchPageModelImpl.Parts.ApplicationHeadline)));
		add(new Label("pageHeadline", labelModel.part(I18NSearchPageModelImpl.Parts.PageHeadline)));
		form.add(group);
		group.add(newButton);
		group.add(changeButton);
		group.add(showButton);
		group.add(new Label("searchTableHeadline", labelModel.part(I18NSearchPageModelImpl.Parts.SearchTableHeadline)));
		
		
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

	
		group.add(new Label("nameHeader", labelModel.part(I18NSearchPageModelImpl.Parts.NameHeaderLabel)));
		group.add(new Label("categoryHeader", labelModel.part(I18NSearchPageModelImpl.Parts.CategoryHeaderLabel)));
		group.add(new Label("dateHeader", labelModel.part(I18NSearchPageModelImpl.Parts.DateHeaderLabel)));
		group.add(new Label("archiveIdHeader", labelModel.part(I18NSearchPageModelImpl.Parts.ArchiveIdHeaderLabel)));
		group.add(persons);
    }
	
	
	
	
	



	@Override
	protected void onBeforeRender() {
		
		labelModel.intoWeb(messageSource);
		super.onBeforeRender();
	}








	
}
