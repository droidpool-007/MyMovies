package com.nishant.mymovies.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.nishant.mymovies.R;
import com.nishant.mymovies.model.ConfigurationModel;
import com.nishant.mymovies.utils.Consts;


/*
 * Created by Nishant on 26-02-2019.
 */
public class MyMoviesApp extends Application {

	private static MyMoviesApp mMyMoviesApp;
	private String mApiKey;
	private String mBaseUrl;
	private String mImageBaseUrl;
	private String mImagePosterBaseUrl;
	private String mImageBackDropBaseUrl;
	private ConfigurationModel mConfigurationModel;


	public static MyMoviesApp getInstance() {
		return mMyMoviesApp;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mMyMoviesApp = this;
		loadConfigurations();
	}

	private void loadConfigurations() {
		mApiKey = getResources().getString(R.string.api_key);
		mBaseUrl = getResources().getString(R.string.base_url);

		SharedPreferences sharedPreferences = getSharedPreferences(Consts.SHARED_PREF, Context.MODE_PRIVATE);
		String configJson = sharedPreferences.getString(Consts.PREF_CONFIG, "");

		if (TextUtils.isEmpty(configJson)) {
			mConfigurationModel = null;
		} else {
			mConfigurationModel = new Gson().fromJson(configJson, ConfigurationModel.class);
			mImageBaseUrl = mConfigurationModel.getImages().getBaseUrl();
			mImagePosterBaseUrl = mConfigurationModel.getImages().getBaseUrl() + mConfigurationModel.getImages().getPosterSizes().get(3);
			mImageBackDropBaseUrl = mConfigurationModel.getImages().getBaseUrl() + mConfigurationModel.getImages().getBackdropSizes().get(1);
		}
	}

	public String getApiKey() {
		return mApiKey;
	}

	public String getBaseUrl() {
		return mBaseUrl;
	}

	public String getImageBaseUrl() {
		return mImageBaseUrl;
	}

	public String getImagePosterBaseUrl() {
		return mImagePosterBaseUrl;
	}

	public String getImageBackDropBaseUrl() {
		return mImageBackDropBaseUrl;
	}

	public ConfigurationModel getConfigurationModel() {
		return mConfigurationModel;
	}

	public void setConfigurationModel(ConfigurationModel mConfigurationModel) {
		this.mConfigurationModel = mConfigurationModel;

		String configJson = new Gson().toJson(mConfigurationModel);
		SharedPreferences.Editor editor = getSharedPreferences(Consts.SHARED_PREF, Context.MODE_PRIVATE).edit();
		editor.putString(Consts.PREF_CONFIG, configJson);
		editor.apply();
	}
}
