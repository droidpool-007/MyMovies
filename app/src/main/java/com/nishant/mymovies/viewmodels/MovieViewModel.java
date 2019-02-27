package com.nishant.mymovies.viewmodels;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.nishant.mymovies.model.ResultsModel;
import com.nishant.mymovies.repository.MovieDataFactory;
import com.nishant.mymovies.repository.MovieDataSource;
import com.nishant.mymovies.repository.SearchMovieDataFactory;
import com.nishant.mymovies.repository.SearchMovieDataSource;
import com.nishant.mymovies.utils.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * Created by nishant on 26/02/19.
 */
public class MovieViewModel extends ViewModel {

	private Executor executor;
	private LiveData<NetworkState> networkState;
	private LiveData<NetworkState> searchNetworkState;
	private LiveData<PagedList<ResultsModel>> resultModelLiveData;
	private LiveData<PagedList<ResultsModel>> searchResultModelLiveData;

	public MovieViewModel() {
		init();
	}

	private void init() {
		executor = Executors.newFixedThreadPool(5);

		MovieDataFactory movieDataFactory = new MovieDataFactory();
		networkState = Transformations.switchMap(movieDataFactory.getMutableLiveData(),
				(Function<MovieDataSource, LiveData<NetworkState>>) MovieDataSource::getNetworkState);

		PagedList.Config pagedListConfig =
				(new PagedList.Config.Builder())
						.setEnablePlaceholders(false)
						.setInitialLoadSizeHint(20)
						.setPageSize(10).build();

		resultModelLiveData = (new LivePagedListBuilder(movieDataFactory, pagedListConfig))
				.setFetchExecutor(executor)
				.build();
	}

	public void searchMovie(String query) {
		SearchMovieDataFactory searchMovieDataFactory = new SearchMovieDataFactory(query);
		searchNetworkState = Transformations.switchMap(searchMovieDataFactory.getSearchMutableLiveData(),
				(Function<SearchMovieDataSource, LiveData<NetworkState>>) SearchMovieDataSource::getNetworkState);

		PagedList.Config pagedListConfig =
				(new PagedList.Config.Builder())
						.setEnablePlaceholders(false)
						.setInitialLoadSizeHint(20)
						.setPageSize(10).build();

		searchResultModelLiveData = (new LivePagedListBuilder(searchMovieDataFactory, pagedListConfig))
				.setFetchExecutor(executor)
				.build();
	}

	/*
	 * Getter method for the network state
	 */
	public LiveData<NetworkState> getNetworkState() {
		return networkState;
	}

	/*
	 * Getter method for the pageList
	 */
	public LiveData<PagedList<ResultsModel>> getResultModelLiveData() {
		return resultModelLiveData;
	}

	/*
	 * Getter method for the search pageList
	 */
	public LiveData<PagedList<ResultsModel>> getSearchResultModelLiveData() {
		return searchResultModelLiveData;
	}

	/*
	 * Getter method for the network state for search
	 */
	public LiveData<NetworkState> getSearchNetworkState() {
		return networkState;
	}
}
