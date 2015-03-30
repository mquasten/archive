package de.mq.archive.web.search;




 interface SearchPageController {

	
	
	
	void search(final SearchPageModel model);

	void firstPage(SearchPageModel model);

	void nextPage(SearchPageModel model);

	void prevoiusPage(SearchPageModel model);

	void lastPage(SearchPageModel model);
	
	

}