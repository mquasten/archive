package de.mq.archive.web.search;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.ArchiveService;


public class SearchPage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private ArchiveService archiveService;

	public SearchPage(final PageParameters parameters) {
		final RadioGroup<Archive> group=new RadioGroup<Archive>("group", new Model<Archive>());
		final Form<Archive> form = new Form<Archive>("form")  {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				System.out.println(group.getDefaultModelObject());
				super.onSubmit();
			}
			
		};
		add(form);
		
		form.add(group);
		
		
		
		
		   final ListView<Archive> persons = new ListView<Archive>("documents", archiveService.archives(null)) {
		  
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem<Archive> item) {
		      item.add(new Radio<Archive>("document", item.getModel() ));
		      item.add(new Label("name", item.getModelObject().name()));
		      item.add(new Label("category", item.getModelObject().category() ));
		 
		    };
		};

	
		group.add(persons);
    }
}
