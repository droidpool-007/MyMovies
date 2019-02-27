package com.nishant.mymovies.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/*
 * Created by nishant on 26/02/19.
 */

@Database(entities = {FavMovieEntity.class}, version = 1, exportSchema = false)
public abstract class FavMoviesDatabase extends RoomDatabase {
	public abstract FavMovieDao favMovieDao();
}
