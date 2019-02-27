package com.nishant.mymovies.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.nishant.mymovies.apis.MovieServiceGenerator;
import com.nishant.mymovies.apis.MoviesService;
import com.nishant.mymovies.application.MyMoviesApp;
import com.nishant.mymovies.model.MovieModel;
import com.nishant.mymovies.model.MoviesListModel;
import com.nishant.mymovies.model.ResultsModel;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created by nishant on 26/02/19.
 */
public class MovieRepository {

	private static MovieRepository movieRepository;

	public synchronized static MovieRepository getInstance() {
		if (movieRepository == null) {
			movieRepository = new MovieRepository();
		}
		return movieRepository;
	}

	public LiveData<List<ResultsModel>> getTopRatedMovies(int currentPage) {
		final MutableLiveData<List<ResultsModel>> resultsModelListLiveData = new MutableLiveData<>();

		MoviesService moviesService = MovieServiceGenerator.createService(MoviesService.class);
		Call<MoviesListModel> call = moviesService.getTopRatedMovies(MyMoviesApp.getInstance().getApiKey(), currentPage);

		call.enqueue(new Callback<MoviesListModel>() {
			@Override
			public void onResponse(Call<MoviesListModel> call, Response<MoviesListModel> response) {
				if (response.isSuccessful()) {
					resultsModelListLiveData.setValue(response.body().getResults());
				}
			}

			@Override
			public void onFailure(Call<MoviesListModel> call, Throwable t) {
				if (t instanceof SocketTimeoutException || t instanceof IOException) {
					resultsModelListLiveData.setValue(null);
//					showErrorDialog("Network Error", "No Network. Please check connection");
				} else {
					resultsModelListLiveData.setValue(null);
//					showErrorDialog("Error", "Something went wrong : " + t.getMessage());
					Log.e("ERROR", "onFailure: Something went wrong", t);
				}
			}
		});

		return resultsModelListLiveData;
	}

	public LiveData<MovieModel> getMovieDetails(int movieId) {
		final MutableLiveData<MovieModel> movieModelMutableLiveData = new MutableLiveData<>();

		MoviesService moviesService = MovieServiceGenerator.createService(MoviesService.class);
		Call<MovieModel> call = moviesService.getMovieDetails(movieId, MyMoviesApp.getInstance().getApiKey());

		call.enqueue(new Callback<MovieModel>() {
			@Override
			public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
				if (response.isSuccessful()) {
					movieModelMutableLiveData.setValue(response.body());
				}
			}

			@Override
			public void onFailure(Call<MovieModel> call, Throwable t) {
				if (t instanceof SocketTimeoutException || t instanceof IOException) {
					movieModelMutableLiveData.setValue(null);
//					showErrorDialog("Network Error", "No Network. Please check connection");
				} else {
					movieModelMutableLiveData.setValue(null);
//					showErrorDialog("Error", "Something went wrong : " + t.getMessage());
					Log.e("ERROR", "onFailure: Something went wrong", t);
				}
			}
		});

		return movieModelMutableLiveData;
	}

}
