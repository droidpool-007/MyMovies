package com.nishant.mymovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 * Created by nishant on 27/02/19.
 */
public class MovieModel {

	/**
	 * adult : false
	 * backdrop_path : /fCayJrkfRaCRCTh8GqN30f8oyQF.jpg
	 * belongs_to_collection : null
	 * budget : 63000000
	 * genres : [{"id":18,"name":"Drama"}]
	 * homepage :
	 * id : 550
	 * imdb_id : tt0137523
	 * original_language : en
	 * original_title : Fight Club
	 * overview : A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground "fight clubs" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.
	 * popularity : 0.5
	 * poster_path : null
	 * production_companies : [{"id":508,"logo_path":"/7PzJdsLGlR7oW4J0J5Xcd0pHGRg.png","name":"Regency Enterprises","origin_country":"US"},{"id":711,"logo_path":null,"name":"Fox 2000 Pictures","origin_country":""},{"id":20555,"logo_path":null,"name":"Taurus Film","origin_country":""},{"id":54050,"logo_path":null,"name":"Linson Films","origin_country":""},{"id":54051,"logo_path":null,"name":"Atman Entertainment","origin_country":""},{"id":54052,"logo_path":null,"name":"Knickerbocker Films","origin_country":""},{"id":25,"logo_path":"/qZCc1lty5FzX30aOCVRBLzaVmcp.png","name":"20th Century Fox","origin_country":"US"}]
	 * production_countries : [{"iso_3166_1":"US","name":"United States of America"}]
	 * release_date : 1999-10-12
	 * revenue : 100853753
	 * runtime : 139
	 * spoken_languages : [{"iso_639_1":"en","name":"English"}]
	 * status : Released
	 * tagline : How much can you know about yourself if you've never been in a fight?
	 * title : Fight Club
	 * video : false
	 * vote_average : 7.8
	 * vote_count : 3439
	 */

	private boolean adult;
	private String backdrop_path;
	private int budget;
	private String homepage;
	private int id;
	private String imdb_id;
	private String original_language;
	private String original_title;
	private String overview;
	private double popularity;
	private String release_date;
	private int revenue;
	private int runtime;
	private String status;
	private String tagline;
	private String title;
	private boolean video;
	private double vote_average;
	private int vote_count;
	private List<GenresModel> genres;
	private List<ProductionCompaniesModel> production_companies;
	private List<ProductionCountriesModel> production_countries;
	private List<SpokenLanguagesModel> spoken_languages;
	/**
	 * belongs_to_collection : {"id":230,"name":"The Godfather Collection","poster_path":"/sSbkKCHtIEakht5rnEjrWeR2LLG.jpg","backdrop_path":"/3WZTxpgscsmoUk81TuECXdFOD0R.jpg"}
	 * popularity : 33.104
	 * poster_path : /rPdtLWNsZmAtoZl9PK7S2wE3qiS.jpg
	 * vote_average : 8.6
	 */

	private BelongsToCollectionModel belongs_to_collection;
	private String poster_path;

	public MovieModel() {
	}

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}


	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImdb_id() {
		return imdb_id;
	}

	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}

	public String getOriginal_language() {
		return original_language;
	}

	public void setOriginal_language(String original_language) {
		this.original_language = original_language;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}


	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public int getRevenue() {
		return revenue;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public double getVote_average() {
		return vote_average;
	}

	public void setVote_average(double vote_average) {
		this.vote_average = vote_average;
	}

	public int getVote_count() {
		return vote_count;
	}

	public void setVote_count(int vote_count) {
		this.vote_count = vote_count;
	}

	public List<GenresModel> getGenres() {
		return genres;
	}

	public void setGenres(List<GenresModel> genres) {
		this.genres = genres;
	}

	public List<ProductionCompaniesModel> getProduction_companies() {
		return production_companies;
	}

	public void setProduction_companies(List<ProductionCompaniesModel> production_companies) {
		this.production_companies = production_companies;
	}

	public List<ProductionCountriesModel> getProduction_countries() {
		return production_countries;
	}

	public void setProduction_countries(List<ProductionCountriesModel> production_countries) {
		this.production_countries = production_countries;
	}

	public List<SpokenLanguagesModel> getSpoken_languages() {
		return spoken_languages;
	}

	public void setSpoken_languages(List<SpokenLanguagesModel> spoken_languages) {
		this.spoken_languages = spoken_languages;
	}

	public BelongsToCollectionModel getBelongs_to_collection() {
		return belongs_to_collection;
	}

	public void setBelongs_to_collection(BelongsToCollectionModel belongs_to_collection) {
		this.belongs_to_collection = belongs_to_collection;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}


	public static class GenresModel {
		/**
		 * id : 18
		 * name : Drama
		 */

		private int id;
		private String name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class ProductionCompaniesModel {
		/**
		 * id : 508
		 * logo_path : /7PzJdsLGlR7oW4J0J5Xcd0pHGRg.png
		 * name : Regency Enterprises
		 * origin_country : US
		 */

		private int id;
		private String logo_path;
		private String name;
		private String origin_country;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getLogo_path() {
			return logo_path;
		}

		public void setLogo_path(String logo_path) {
			this.logo_path = logo_path;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOrigin_country() {
			return origin_country;
		}

		public void setOrigin_country(String origin_country) {
			this.origin_country = origin_country;
		}
	}

	public static class ProductionCountriesModel {
		/**
		 * iso_3166_1 : US
		 * name : United States of America
		 */

		private String iso_3166_1;
		private String name;

		public String getIso_3166_1() {
			return iso_3166_1;
		}

		public void setIso_3166_1(String iso_3166_1) {
			this.iso_3166_1 = iso_3166_1;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class SpokenLanguagesModel {
		/**
		 * iso_639_1 : en
		 * name : English
		 */

		private String iso_639_1;
		private String name;

		public String getIso_639_1() {
			return iso_639_1;
		}

		public void setIso_639_1(String iso_639_1) {
			this.iso_639_1 = iso_639_1;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class BelongsToCollectionModel {
		/**
		 * id : 230
		 * name : The Godfather Collection
		 * poster_path : /sSbkKCHtIEakht5rnEjrWeR2LLG.jpg
		 * backdrop_path : /3WZTxpgscsmoUk81TuECXdFOD0R.jpg
		 */

		@SerializedName("id")
		private int idX;
		private String name;
		private String poster_path;
		@SerializedName("backdrop_path")
		private String backdrop_pathX;

		public int getIdX() {
			return idX;
		}

		public void setIdX(int idX) {
			this.idX = idX;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPoster_path() {
			return poster_path;
		}

		public void setPoster_path(String poster_path) {
			this.poster_path = poster_path;
		}

		public String getBackdrop_pathX() {
			return backdrop_pathX;
		}

		public void setBackdrop_pathX(String backdrop_pathX) {
			this.backdrop_pathX = backdrop_pathX;
		}
	}
}
