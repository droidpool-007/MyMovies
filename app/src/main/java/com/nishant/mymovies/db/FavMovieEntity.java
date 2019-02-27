package com.nishant.mymovies.db;

/*
 * Created by nishant on 26/02/19.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "fav_movie")
public class FavMovieEntity {

	@PrimaryKey
	private int id;

	@ColumnInfo(name = "title")
	private String title;

	@ColumnInfo(name = "original_title")
	private String original_title;

	@ColumnInfo(name = "original_language")
	private String original_language;

	@ColumnInfo(name = "release_date")
	private String release_date;

	@ColumnInfo(name = "overview")
	private String overview;

	@ColumnInfo(name = "poster_path")
	private String poster_path;

	@ColumnInfo(name = "backdrop_path")
	private String backdrop_path;

	public FavMovieEntity() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public String getOriginal_language() {
		return original_language;
	}

	public void setOriginal_language(String original_language) {
		this.original_language = original_language;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}
}
