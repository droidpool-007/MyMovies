package com.nishant.mymovies.db;

import com.nishant.mymovies.utils.MockTestUtil;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by nishant on 27/02/19.
 */
public class FavMovieDaoTest extends DbTest {

	@Test
	public void insertAndReadMovieTest() {
		int id = 444;
		List<FavMovieEntity> favMovieEntities = new ArrayList<>();
		favMovieEntities.addAll(MockTestUtil.mockFavMovieList(id));

		for (FavMovieEntity favMovieEntity : favMovieEntities) {
			db.favMovieDao().insertFavMovie(favMovieEntity);
		}

		List<FavMovieEntity> storedFavMovieEntities = db.favMovieDao().getAllFavMovies().getValue();
		Assert.assertEquals(id, storedFavMovieEntities.get(0).getId());
		Assert.assertEquals(id + 1, storedFavMovieEntities.get(1).getId());
	}
}
