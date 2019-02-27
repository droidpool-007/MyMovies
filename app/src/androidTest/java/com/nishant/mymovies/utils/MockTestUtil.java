package com.nishant.mymovies.utils;

import com.nishant.mymovies.db.FavMovieEntity;
import com.nishant.mymovies.model.ResultsModel;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by nishant on 27/02/19.
 */
public class MockTestUtil {

	public static List<FavMovieEntity> mockFavMovieList(int id) {
		List<FavMovieEntity> favMovieEntities = new ArrayList<>();

		FavMovieEntity favMovieEntity1 = new FavMovieEntity();
		favMovieEntity1.setId(id);
		favMovieEntities.add(favMovieEntity1);

		FavMovieEntity favMovieEntity2 = new FavMovieEntity();
		favMovieEntity2.setId(id + 1);
		favMovieEntities.add(favMovieEntity2);

		return favMovieEntities;
	}

	public static FavMovieEntity mockFavMovie(String type) {
		FavMovieEntity favMovieEntity = new FavMovieEntity();
		favMovieEntity.setId(1);
		favMovieEntity.setTitle("ABC");
		return favMovieEntity;
	}

	public static List<ResultsModel> mockResultsModelList(int id) {
		List<ResultsModel> resultsModelsList = new ArrayList<>();

		ResultsModel resultsModel1 = new ResultsModel();
		resultsModel1.setId(id);
		resultsModelsList.add(resultsModel1);

		ResultsModel resultsModel2 = new ResultsModel();
		resultsModel2.setId(id + 1);
		resultsModelsList.add(resultsModel2);

		return resultsModelsList;
	}

}
