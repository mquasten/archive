package de.mq.archive.web.search;




	enum I18NSearchPageModelParts {
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
		I18NSearchPageModelParts(final String key) {
			this.key=key;
		}
	}
	
	