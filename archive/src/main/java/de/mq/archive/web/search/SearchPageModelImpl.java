package de.mq.archive.web.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.util.StringUtils;

import de.mq.archive.domain.Archive;
import de.mq.archive.domain.support.ArchiveImpl;
import de.mq.archive.domain.support.ModifyablePaging;
import de.mq.archive.web.BasicEnumModelImpl;
import de.mq.archive.web.OneWayMapping;
import de.mq.archive.web.TwoWayMapping;

class SearchPageModelImpl implements SearchPageModel, SearchPageModelWeb {
	
	private final TwoWayMapping<Archive, Enum<?>> searchCriteria; 
	private final List<Archive> archives = new ArrayList<>();
	private final IModel<String> selectedArchive = new Model<>();; 

	
	private ModifyablePaging paging ; 
	
	private final OneWayMapping<Locale, Enum<?>>  labels;
	private Number pageSize; 
	
	SearchPageModelImpl(final TwoWayMapping<Archive, Enum<?>> searchCriteria, final OneWayMapping<Locale, Enum<?>>  labels,   final  Number pageSize) {
		this.searchCriteria = searchCriteria;
		this.labels=labels;
		this.pageSize = pageSize;
	}

	
	@Override
	public final Archive getSearchCriteria() {
		return searchCriteria.toDomain();
	}

	
	@Override
	public final String getSelectedArchiveId() {
		if ( StringUtils.isEmpty(selectedArchive.getObject())) {
			return "";
		}
		return selectedArchive.getObject();
	}
	
	final List<Archive> getArchives() {
		
		return Collections.unmodifiableList(archives);
	}
	
	@Override
	public final List<TwoWayMapping<Archive, Enum<?>>>  getArchivesWeb2() {
		final List<TwoWayMapping<Archive, Enum<?>>> results = new ArrayList<>();
		archives.forEach(archive -> { 
			final TwoWayMapping<Archive, Enum<?>> model =  new BasicEnumModelImpl<>(Arrays.asList(ArchiveModelParts.values()), ArchiveImpl.class); 
			model.intoWeb(archive);
			results.add(model);
		});
		return results;
	}
	
	@Override
	public final void setArchives(final List<Archive> archives) {
		this.archives.clear();
		this.archives.addAll(archives);
		
		unSelectIfNotInResult();
	}
	
	private final void unSelectIfNotInResult() {
		
		if( getArchives().stream().filter(archive -> archive.id().equals(getSelectedArchiveId())).findFirst().isPresent() ){
			return;
		}
		selectedArchive.setObject(null);
	}
	
	@Override
	public final Number getPageSize() {
		return pageSize;
	}
	
	
	@Override
	public final TwoWayMapping<Archive, Enum<?>> getSearchCriteriaWeb() {
		return searchCriteria;
	}


	@Override
	public final IModel<String> getSelectedArchiveWeb() {
		return selectedArchive;
	}


	

	@Override
	public final boolean isSelected() {
		return StringUtils.hasText(getSelectedArchiveWeb().getObject());
	}
	
	@Override
	public  final OneWayMapping<Locale, Enum<?>>  getI18NLabels() {
		return labels;
	}
	
	@Override
	public final IModel<String> getPagingInfo() {
		if( paging == null){
			return new Model<>("");
		}
		return  new Model<>(paging.currentPage() + "/" + paging.maxPages());
		
	}
	
	@Override
	public final Optional<ModifyablePaging> getPaging() {
		if (paging == null) {
			return Optional.empty();
		}
		return Optional.of(paging);
		
	}
	
	@Override
	public final void setPaging(final ModifyablePaging paging){
		this.paging=paging;
	}
	
	@Override
	public final boolean hasPaging() {
		return this.paging != null;
	}
	
	@Override
	public final boolean isNotFirstPage() {
		return ! doInPaging(paging -> paging.isBegin());
	}
	
	@Override
	public final boolean hasNextPage() {
		return doInPaging(paging -> paging.hasNextPage());
	}
	
	
	@Override
	public final boolean hasPriviousPage() {
		return  doInPaging(paging -> paging.hasPreviousPage());
	}
	
	@Override
	public final boolean isNotLastPage() {
		return ! doInPaging(paging -> paging.isEnd());
	}
	
	
	private boolean doInPaging(final SearchPageModel.PagingOperation<Boolean> pagingOperation) {
		if(paging ==null){ 
			return false;
		}
		return pagingOperation.execute(paging);
	}


}
