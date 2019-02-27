package com.nishant.mymovies.viewmodel;

/*
 * Created by nishant on 27/02/19.
 */

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.nishant.mymovies.model.ResultsModel;
import com.nishant.mymovies.utils.MockTestUtil;
import com.nishant.mymovies.viewmodels.MovieViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResultsListViewModelTest {

	@Rule
	public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

	@Mock
	Observer<PagedList<ResultsModel>> observer;

	private MovieViewModel movieViewModel;
	private int id = 777;

	@Before
	public void init() {
		movieViewModel = new MovieViewModel();
	}

	@Test
	public void fetchMovies() {
		movieViewModel.getResultModelLiveData().observeForever(observer);
		assert (movieViewModel.getResultModelLiveData().getValue().snapshot() == MockTestUtil.mockResultsModelList(id));
	}

}
