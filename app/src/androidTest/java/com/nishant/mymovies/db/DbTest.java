package com.nishant.mymovies.db;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;

/*
 * Created by nishant on 27/02/19.
 */
public abstract class DbTest {
	protected FavMoviesDatabase db;

	@Before
	public void initDb() {
		db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), FavMoviesDatabase.class).build();
	}

	@After
	public void closeDb() {
		db.close();
	}
}
