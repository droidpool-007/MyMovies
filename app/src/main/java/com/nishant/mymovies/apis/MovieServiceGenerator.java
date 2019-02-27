package com.nishant.mymovies.apis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nishant.mymovies.application.MyMoviesApp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Created by Nishant on 22-05-2016.
 */
public class MovieServiceGenerator {

	private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

	private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.connectTimeout(1500, TimeUnit.SECONDS)
			.readTimeout(1500, TimeUnit.SECONDS);

	private static Gson gson = new GsonBuilder()
			.setLenient()
			.create();

	private static Retrofit.Builder builder = new Retrofit.Builder()
			.baseUrl(MyMoviesApp.getInstance().getBaseUrl())
			.addConverterFactory(GsonConverterFactory.create(gson));


	public static Retrofit retrofit() {
		return builder.client(httpClient.build()).build();
	}

	public static <S> S createService(Class<S> serviceClass) {
		Retrofit retrofit = builder.client(httpClient.build()).build();
		return retrofit.create(serviceClass);
	}
}

