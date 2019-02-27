package com.nishant.mymovies.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

/*
 * Created by nishant on 26/02/19.
 */
public class SearchMovieDataFactory extends DataSource.Factory {

	private String query;
	private MutableLiveData<SearchMovieDataSource> searchMutableLiveData;
	private SearchMovieDataSource searchMovieDataSource;

	public SearchMovieDataFactory(String searchQuery) {
		this.searchMutableLiveData = new MutableLiveData<>();
		this.query = searchQuery;
	}

	@Override
	public DataSource create() {
		searchMovieDataSource = new SearchMovieDataSource(query);
		searchMutableLiveData.postValue(searchMovieDataSource);
		return searchMovieDataSource;
	}

	public MutableLiveData<SearchMovieDataSource> getSearchMutableLiveData() {
		return searchMutableLiveData;
	}
}
