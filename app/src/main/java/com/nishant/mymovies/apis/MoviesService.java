package com.nishant.mymovies.apis;

import com.nishant.mymovies.model.ConfigurationModel;
import com.nishant.mymovies.model.MovieModel;
import com.nishant.mymovies.model.MoviesListModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
 * Created by nishant on 26/02/19.
 */
public interface MoviesService {
	@GET("movie/top_rated")
	Call<MoviesListModel> getTopRatedMovies(
			@Query("api_key") String apiKey,
			@Query("page") int pageIndex
	);

	@GET("search/movie")
	Call<MoviesListModel> searchMovies(
			@Query("api_key") String apiKey,
			@Query("query") String query,
			@Query("page") int pageIndex
	);


	@GET("movie/{movie_id}")
	Call<MovieModel> getMovieDetails(
			@Path("movie_id") int movieid,
			@Query("api_key") String apiKey
	);

	@GET("configuration")
	@Headers("Content-Type: application/octet-stream")
	Call<ConfigurationModel> getConfiguration(
			@Query("api_key") String apiKey
	);


}
