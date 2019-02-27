package com.nishant.mymovies.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

/*
 * Created by nishant on 26/02/19.
 */
public class MovieDataFactory extends DataSource.Factory {

	private MutableLiveData<MovieDataSource> mutableLiveData;
	private MovieDataSource movieDataSource;

	public MovieDataFactory() {
		this.mutableLiveData = new MutableLiveData<>();
	}

	@Override
	public DataSource create() {
		movieDataSource = new MovieDataSource();
		mutableLiveData.postValue(movieDataSource);
		return movieDataSource;
	}

	public MutableLiveData<MovieDataSource> getMutableLiveData() {
		return mutableLiveData;
	}
}
