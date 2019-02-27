package com.nishant.mymovies.model;

import java.util.List;

/*
 * Created by nishant on 26/02/19.
 */
public class ConfigurationModel {

	/**
	 * images : {"base_url":"http://image.tmdb.org/t/p/","secure_base_url":"https://image.tmdb.org/t/p/","backdrop_sizes":["w300","w780","w1280","original"],"logo_sizes":["w45","w92","w154","w185","w300","w500","original"],"poster_sizes":["w92","w154","w185","w342","w500","w780","original"],"profile_sizes":["w45","w185","h632","original"],"still_sizes":["w92","w185","w300","original"]}
	 * change_keys : ["adult","air_date","also_known_as","alternative_titles","biography","birthday","budget","cast","certifications","character_names","created_by","crew","deathday","episode","episode_number","episode_run_time","freebase_id","freebase_mid","general","genres","guest_stars","homepage","images","imdb_id","languages","name","network","origin_country","original_name","original_title","overview","parts","place_of_birth","plot_keywords","production_code","production_companies","production_countries","releases","revenue","runtime","season","season_number","season_regular","spoken_languages","status","tagline","title","translations","tvdb_id","tvrage_id","type","video","videos"]
	 */

	private ImagesModel images;

	public ConfigurationModel() {

	}

	public ImagesModel getImages() {
		return images;
	}

	public void setImages(ImagesModel images) {
		this.images = images;
	}


	public static class ImagesModel {
		/**
		 * base_url : http://image.tmdb.org/t/p/
		 * secure_base_url : https://image.tmdb.org/t/p/
		 * backdrop_sizes : ["w300","w780","w1280","original"]
		 * logo_sizes : ["w45","w92","w154","w185","w300","w500","original"]
		 * poster_sizes : ["w92","w154","w185","w342","w500","w780","original"]
		 * profile_sizes : ["w45","w185","h632","original"]
		 * still_sizes : ["w92","w185","w300","original"]
		 */

		private String base_url;
		private String secure_base_url;
		private List<String> backdrop_sizes;
		private List<String> logo_sizes;
		private List<String> poster_sizes;
		private List<String> profile_sizes;
		private List<String> still_sizes;

		public String getBaseUrl() {
			return base_url;
		}

		public void setBaseUrl(String baseUrl) {
			this.base_url = base_url;
		}

		public String getSecureBaseUrl() {
			return secure_base_url;
		}

		public void setSecureBaseUrl(String secure_base_url) {
			this.secure_base_url = secure_base_url;
		}

		public List<String> getBackdropSizes() {
			return backdrop_sizes;
		}

		public void setBackdropSizes(List<String> backdrop_sizes) {
			this.backdrop_sizes = backdrop_sizes;
		}

		public List<String> getLogoSizes() {
			return logo_sizes;
		}

		public void setLogoSizes(List<String> logo_sizes) {
			this.logo_sizes = logo_sizes;
		}

		public List<String> getPosterSizes() {
			return poster_sizes;
		}

		public void setPosterSizes(List<String> poster_sizes) {
			this.poster_sizes = poster_sizes;
		}

		public List<String> getProfileSizes() {
			return profile_sizes;
		}

		public void setProfileSizes(List<String> profile_sizes) {
			this.profile_sizes = profile_sizes;
		}

		public List<String> getStillSizes() {
			return still_sizes;
		}

		public void setStillSizes(List<String> still_sizes) {
			this.still_sizes = still_sizes;
		}
	}
}
