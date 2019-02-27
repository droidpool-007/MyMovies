package com.nishant.mymovies.repository;

/*
 * Created by nishant on 26/02/19.
 */

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.nishant.mymovies.db.FavMovieEntity;
import com.nishant.mymovies.db.FavMoviesDatabase;
import com.nishant.mymovies.model.ResultsModel;

import java.util.List;

public class FavMovieRepository {

	private static FavMoviesDatabase favMoviesDatabase;
	private String DB_NAME = "db_my_movies";

	public FavMovieRepository(Context context) {
		favMoviesDatabase = Room.databaseBuilder(context, FavMoviesDatabase.class, DB_NAME).build();
	}

	public void insertFavMovie(ResultsModel resultsModel) {
		FavMovieEntity favMovieEntity = new FavMovieEntity();
		favMovieEntity.setId(resultsModel.getId());
		favMovieEntity.setTitle(resultsModel.getTitle());
		favMovieEntity.setOriginal_title(resultsModel.getOriginalTitle());
		favMovieEntity.setOriginal_language(resultsModel.getOriginalLanguage());
		favMovieEntity.setRelease_date(resultsModel.getReleaseDate());
		favMovieEntity.setOverview(resultsModel.getOverview());
		favMovieEntity.setPoster_path(resultsModel.getPosterPath());
		favMovieEntity.setBackdrop_path(resultsModel.getBackdropPath());
		new InsertFavMovieTask(favMovieEntity).execute();
	}

	public void updateFavMovie(ResultsModel resultsModel) {
		FavMovieEntity favMovieEntity = new FavMovieEntity();
		favMovieEntity.setId(resultsModel.getId());
		favMovieEntity.setTitle(resultsModel.getTitle());
		favMovieEntity.setOriginal_title(resultsModel.getOriginalTitle());
		favMovieEntity.setOriginal_language(resultsModel.getOriginalLanguage());
		favMovieEntity.setRelease_date(resultsModel.getReleaseDate());
		favMovieEntity.setOverview(resultsModel.getOverview());
		favMovieEntity.setPoster_path(resultsModel.getPosterPath());
		favMovieEntity.setBackdrop_path(resultsModel.getBackdropPath());
		new UpdateFavMovieTask(favMovieEntity).execute();
	}

	public void deleteFavMovie(ResultsModel resultsModel) {
		FavMovieEntity favMovieEntity = new FavMovieEntity();
		favMovieEntity.setId(resultsModel.getId());
		favMovieEntity.setTitle(resultsModel.getTitle());
		favMovieEntity.setOriginal_title(resultsModel.getOriginalTitle());
		favMovieEntity.setOriginal_language(resultsModel.getOriginalLanguage());
		favMovieEntity.setRelease_date(resultsModel.getReleaseDate());
		favMovieEntity.setOverview(resultsModel.getOverview());
		favMovieEntity.setPoster_path(resultsModel.getPosterPath());
		favMovieEntity.setBackdrop_path(resultsModel.getBackdropPath());
		new DeleteFavMovieTask(favMovieEntity).execute();
	}

	public void deleteFavMovie(FavMovieEntity favMovieEntity) {
		new DeleteFavMovieTask(favMovieEntity).execute();
	}

	public LiveData<List<FavMovieEntity>> getAllFavMovies() {
		return favMoviesDatabase.favMovieDao().getAllFavMovies();
	}

	public LiveData<List<Integer>> getAllFavMoviesIds() {
		return favMoviesDatabase.favMovieDao().getAllFavMoviesIds();
	}

	private static class InsertFavMovieTask extends AsyncTask<Void, Void, Void> {
		private FavMovieEntity mFavMovieEntity;

		InsertFavMovieTask(FavMovieEntity favMovieEntity) {
			this.mFavMovieEntity = favMovieEntity;
		}

		@Override
		protected Void doInBackground(Void... voids) {
			FavMovieEntity favMovieEntity = favMoviesDatabase.favMovieDao().checkIfExists(mFavMovieEntity.getId());
			if (favMovieEntity == null) {
				favMoviesDatabase.favMovieDao().insertFavMovie(mFavMovieEntity);
			}
			return null;
		}
	}

	private static class UpdateFavMovieTask extends AsyncTask<Void, Void, Void> {
		private FavMovieEntity mFavMovieEntity;

		UpdateFavMovieTask(FavMovieEntity favMovieEntity) {
			this.mFavMovieEntity = favMovieEntity;
		}

		@Override
		protected Void doInBackground(Void... voids) {
			favMoviesDatabase.favMovieDao().updateFavMovie(mFavMovieEntity);
			return null;
		}
	}

	private static class DeleteFavMovieTask extends AsyncTask<Void, Void, Void> {
		private FavMovieEntity mFavMovieEntity;

		DeleteFavMovieTask(FavMovieEntity favMovieEntity) {
			this.mFavMovieEntity = favMovieEntity;
		}

		@Override
		protected Void doInBackground(Void... voids) {
			favMoviesDatabase.favMovieDao().deleteFavMovie(mFavMovieEntity);
			return null;
		}
	}

}
