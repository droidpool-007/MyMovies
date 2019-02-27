package com.nishant.mymovies.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nishant.mymovies.R;

/*
 * Created by nishant on 24/12/16.
 */

public class GlideLoader {
	static public DrawableRequestBuilder<String> url(Context context) {
		return Glide.with(context)
				.fromString()
				.placeholder(R.drawable.ic_no_movies_small)
				.error(R.drawable.ic_no_movies_small)
				.dontAnimate()
				.fitCenter()
				.diskCacheStrategy(DiskCacheStrategy.ALL);
	}

	static public DrawableRequestBuilder<String> url(Fragment fragment) {
		return Glide.with(fragment)
				.fromString()
				.placeholder(R.drawable.ic_no_movies_small)
				.error(R.drawable.ic_no_movies_small)
				.dontAnimate()
				.fitCenter()
				.diskCacheStrategy(DiskCacheStrategy.ALL);
	}

	static public DrawableRequestBuilder<String> url(Activity activity) {
		return Glide.with(activity)
				.fromString()
				.placeholder(R.drawable.ic_no_movies_small)
				.error(R.drawable.ic_no_movies_small)
				.dontAnimate()
				.fitCenter()
				.diskCacheStrategy(DiskCacheStrategy.ALL);
	}
}
