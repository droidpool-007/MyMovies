package com.nishant.mymovies.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;

import com.nishant.mymovies.model.ResultsModel;
import com.nishant.mymovies.utils.MockTestUtil;
import com.nishant.mymovies.viewmodels.MovieViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

/*
 * Created by nishant on 27/02/19.
 */
public class SearchResultViewModelTest {

	@Rule
	public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
	@Mock
	Observer<PagedList<ResultsModel>> observer;
	private MovieViewModel searchMovieViewModel;

	@Before
	public void init() {
		searchMovieViewModel = new MovieViewModel();
	}

	@Test
	public void searchMovies() {

		searchMovieViewModel.getSearchResultModelLiveData().observeForever(observer);
		searchMovieViewModel.searchMovie("thor");

		assert (searchMovieViewModel.getSearchResultModelLiveData().getValue().snapshot() == MockTestUtil.mockResultsModelList(777));
	}


}
