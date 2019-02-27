package com.nishant.mymovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by nishant on 26/02/19.
 */
public class ResultsModel implements Parcelable {

	public static final Parcelable.Creator<ResultsModel> CREATOR = new Parcelable.Creator<ResultsModel>() {
		@Override
		public ResultsModel createFromParcel(Parcel source) {
			return new ResultsModel(source);
		}

		@Override
		public ResultsModel[] newArray(int size) {
			return new ResultsModel[size];
		}
	};

	public static DiffUtil.ItemCallback<ResultsModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<ResultsModel>() {
		@Override
		public boolean areItemsTheSame(@NonNull ResultsModel oldResultsModel, @NonNull ResultsModel newResultsModel) {

			return oldResultsModel.id == newResultsModel.id;
		}

		@Override
		public boolean areContentsTheSame(@NonNull ResultsModel oldResultsModel, @NonNull ResultsModel newResultsModel) {
			return oldResultsModel.equals(newResultsModel);
		}
	};


	/**
	 * vote_count : 1987
	 * id : 19404
	 * video : false
	 * vote_average : 9.1
	 * title : Dilwale Dulhania Le Jayenge
	 * popularity : 19.221
	 * poster_path : /uC6TTUhPpQCmgldGyYveKRAu8JN.jpg
	 * original_language : hi
	 * original_title : दिलवाले दुल्हनिया ले जायेंगे
	 * genre_ids : [35,18,10749]
	 * backdrop_path : /nl79FQ8xWZkhL3rDr1v2RFFR6J0.jpg
	 * adult : false
	 * overview : Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.
	 * release_date : 1995-10-20
	 */

	private int vote_count;
	private int id;
	private boolean video;
	private double vote_average;
	private String title;
	private double popularity;
	private String poster_path;
	private String original_language;
	private String original_title;
	private String backdrop_path;
	private boolean adult;
	private String overview;
	private String release_date;
	private List<Integer> genre_ids;

	public ResultsModel() {
	}

	protected ResultsModel(Parcel in) {
		this.vote_count = in.readInt();
		this.id = in.readInt();
		this.video = in.readByte() != 0;
		this.vote_average = in.readDouble();
		this.title = in.readString();
		this.popularity = in.readDouble();
		this.poster_path = in.readString();
		this.original_language = in.readString();
		this.original_title = in.readString();
		this.backdrop_path = in.readString();
		this.adult = in.readByte() != 0;
		this.overview = in.readString();
		this.release_date = in.readString();
		this.genre_ids = new ArrayList<Integer>();
		in.readList(this.genre_ids, Integer.class.getClassLoader());
	}

	public int getVoteCount() {
		return vote_count;
	}

	public void setVoteCount(int vote_count) {
		this.vote_count = vote_count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public double getVoteAverage() {
		return vote_average;
	}

	public void setVoteAverage(double vote_average) {
		this.vote_average = vote_average;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public String getPosterPath() {
		return poster_path;
	}

	public void setPosterPath(String poster_path) {
		this.poster_path = poster_path;
	}

	public String getOriginalLanguage() {
		return original_language;
	}

	public void setOriginalLanguage(String original_language) {
		this.original_language = original_language;
	}

	public String getOriginalTitle() {
		return original_title;
	}

	public void setOriginalTitle(String original_title) {
		this.original_title = original_title;
	}

	public String getBackdropPath() {
		return backdrop_path;
	}

	public void setBackdropPath(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getReleaseDate() {
		return release_date;
	}

	public void setReleaseDate(String release_date) {
		this.release_date = release_date;
	}

	public List<Integer> getGenreIds() {
		return genre_ids;
	}

	public void setGenreIds(List<Integer> genre_ids) {
		this.genre_ids = genre_ids;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.vote_count);
		dest.writeInt(this.id);
		dest.writeByte(this.video ? (byte) 1 : (byte) 0);
		dest.writeDouble(this.vote_average);
		dest.writeString(this.title);
		dest.writeDouble(this.popularity);
		dest.writeString(this.poster_path);
		dest.writeString(this.original_language);
		dest.writeString(this.original_title);
		dest.writeString(this.backdrop_path);
		dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
		dest.writeString(this.overview);
		dest.writeString(this.release_date);
		dest.writeList(this.genre_ids);
	}
}
