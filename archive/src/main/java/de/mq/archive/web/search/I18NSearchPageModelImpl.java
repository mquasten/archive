package de.mq.archive.web.search;

import java.util.Arrays;
import java.util.stream.Collectors;

public class I18NSearchPageModelImpl  extends BasicI18NEnumModelImpl{

	
	private static final long serialVersionUID = 1L;

	enum Parts {
		SearchCriteriaHeadline("archive_search_search_headline"),
		SearchNameLabel("archive_search_search_name"),
		
		SearchCategoryLabel("archive_search_search_category"),
		
		SearchArchiveLabel("archive_search_search_archive"),
		
		SearchButton("archive_search_search_button"),
		
		NameHeaderLabel("archive_search_header_name"), 
		
		CategoryHeaderLabel("archive_search_header_category"), 
		DateHeaderLabel("archive_search_header_date"),
		ArchiveIdHeaderLabel("archive_search_header_archive"),
		
		
		NewButton("archive_search_button_new"),
		ChangeButton("archive_search_button_change"),
		ShowButton("archive_search_button_show"),
		
		SearchTableHeadline("archive_search_table_headline"),
		
		ApplicationHeadline("archive_headline"),
		PageHeadline("archive_search_headline");
		
		final String key;
		Parts(final String key) {
			this.key=key;
		}
	}
	
	protected I18NSearchPageModelImpl() {
		
		super(Arrays.asList(Parts.values()),   Arrays.asList(Parts.values()).stream().map( value -> value.key.toString()).collect(Collectors.toList()));
	}

}
