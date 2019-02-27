package com.nishant.mymovies.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.nishant.mymovies.apis.MovieServiceGenerator;
import com.nishant.mymovies.apis.MoviesService;
import com.nishant.mymovies.application.MyMoviesApp;
import com.nishant.mymovies.model.ResultsModel;
import com.nishant.mymovies.model.MoviesListModel;
import com.nishant.mymovies.utils.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created by nishant on 26/02/19.
 */
public class MovieDataSource extends PageKeyedDataSource<Integer, ResultsModel> {


	private MutableLiveData networkState;
	private MutableLiveData initialLoading;

	public MovieDataSource() {

		networkState = new MutableLiveData();
		initialLoading = new MutableLiveData();
	}

	public MutableLiveData getNetworkState() {
		return networkState;
	}

	public MutableLiveData getInitialLoading() {
		return initialLoading;
	}


	@Override
	public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, ResultsModel> callback) {
		initialLoading.postValue(NetworkState.LOADING);
		networkState.postValue(NetworkState.LOADING);

		MoviesService moviesService = MovieServiceGenerator.createService(MoviesService.class);
		Call<MoviesListModel> call = moviesService.getTopRatedMovies(MyMoviesApp.getInstance().getApiKey(), 1);

		call.enqueue(new Callback<MoviesListModel>() {
			@Override
			public void onResponse(Call<MoviesListModel> call, Response<MoviesListModel> response) {
				if (response.isSuccessful()) {
					MoviesListModel moviesListModel = response.body();
					if (moviesListModel != null && moviesListModel.getResults() != null) {
						callback.onResult(moviesListModel.getResults(), null, 2);
						initialLoading.postValue(NetworkState.LOADED);
						networkState.postValue(NetworkState.LOADED);
					} else {
						initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
						networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
					}
				} else {
					initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
					networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
				}
			}

			@Override
			public void onFailure(Call<MoviesListModel> call, Throwable t) {
				String errorMessage = t == null ? "unknown error" : t.getMessage();
				networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
			}
		});
	}

	@Override
	public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResultsModel> callback) {

	}

	@Override
	public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResultsModel> callback) {

		networkState.postValue(NetworkState.LOADING);

		MoviesService moviesService = MovieServiceGenerator.createService(MoviesService.class);
		Call<MoviesListModel> call = moviesService.getTopRatedMovies(MyMoviesApp.getInstance().getApiKey(), params.key);

		call.enqueue(new Callback<MoviesListModel>() {
			@Override
			public void onResponse(Call<MoviesListModel> call, Response<MoviesListModel> response) {
				if (response.isSuccessful()) {
					MoviesListModel moviesListModel = response.body();
					if (moviesListModel != null && moviesListModel.getResults() != null) {
						callback.onResult(moviesListModel.getResults(), params.key + 1);
						networkState.postValue(NetworkState.LOADED);
					} else {
						networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
					}
				} else {
					networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
				}
			}

			@Override
			public void onFailure(Call<MoviesListModel> call, Throwable t) {
				networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
			}
		});
	}
}
