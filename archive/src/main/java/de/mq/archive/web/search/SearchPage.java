package de.mq.archive.web.search;



import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.context.MessageSource;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;


public class SearchPage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ArchiveService archiveService;
	
	@Inject
	private MessageSource messageSource;


	private final Model<String> nameHeaderLabelModel = new Model<>();
	private final Model<String> categoryHeaderLabelModel = new Model<>();
	private final Model<String> dateHeaderLabelModel = new Model<>();
	private final Model<String> archiveIdHeaderLabelModel = new Model<>();
	private final Model<String> newButtonModel = new Model<>();
	private final Model<String> changeButtonModel = new Model<>();
	private final Model<String> showButtonModel = new Model<>();
	private final Model<String> searchTableHeadlineModel = new Model<>();
	
	private final Model<String> applicationHeadlineModel = new Model<>();
	private final Model<String> pageHeadlineModel = new Model<>();
	
	
	
	public SearchPage(final PageParameters parameters) {
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
