package com.nishant.mymovies.db;

/*
 * Created by nishant on 26/02/19.
 */

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavMovieDao {

	@Query("SELECT * FROM fav_movie")
	LiveData<List<FavMovieEntity>> getAllFavMovies();

	@Query("SELECT id FROM fav_movie")
	LiveData<List<Integer>> getAllFavMoviesIds();

	@Query("SELECT * FROM fav_movie WHERE id =:movieId")
	FavMovieEntity checkIfExists(int movieId);

	@Insert
	Long insertFavMovie(FavMovieEntity favMovieEntity);

	@Delete
	void deleteFavMovie(FavMovieEntity favMovieEntity);

	@Update
	void updateFavMovie(FavMovieEntity favMovieEntity);
}
