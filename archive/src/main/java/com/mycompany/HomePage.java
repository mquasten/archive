package com.mycompany;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.WebPage;


public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
	
		Form<String> form = new Form<String>("form"); 
		
		add(form);
		RadioGroup<String> group=new RadioGroup<String>("group");
		form.add(group);
		List<String> result = new ArrayList<String>();
		result.add("Kylie");
		result.add("Madonna");
		final ListView<String> persons = new ListView<String>("persons", result) {
		  
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem<String> item) {
		      item.add(new Radio<String>("radio", item.getModel()));
		      item.add(new Label("name", item.getModelObject().toString()));
		      item.add(new Label("lastName", item.getModelObject().toString() ));
		    System.out.println("***");
		    };
		};

		group.add(persons);
    }
}
