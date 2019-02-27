package com.nishant.mymovies.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nishant.mymovies.R;
import com.nishant.mymovies.application.MyMoviesApp;
import com.nishant.mymovies.customviews.CustomScrollView;
import com.nishant.mymovies.model.MovieModel;
import com.nishant.mymovies.model.ResultsModel;
import com.nishant.mymovies.repository.MovieRepository;
import com.nishant.mymovies.utils.Consts;
import com.nishant.mymovies.utils.GlideLoader;

import java.text.NumberFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends BaseActivity {

	@BindView(R.id.activity_movie_details_iv_header)
	ImageView mIvHeader;
	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.activity_movie_details_collapsing_toolbar)
	CollapsingToolbarLayout mCollapsingToolbar;
	@BindView(R.id.activity_movie_details_scrollview)
	CustomScrollView mScrollview;
	@BindView(R.id.activity_movie_details_fab)
	FloatingActionButton mFab;
	@BindView(R.id.activity_movie_details_appbarlayout)
	AppBarLayout mAppbarlayout;
	@BindView(R.id.activity_movie_details_tv_title)
	TextView mTvTitle;
	@BindView(R.id.activity_movie_details_img_poster)
	ImageView mIvPoster;
	@BindView(R.id.activity_movie_details_tv_release_date)
	TextView mTvReleaseDate;
	@BindView(R.id.activity_movie_details_tv_vote)
	TextView mTvVote;
	@BindViews({
			R.id.activity_movie_details_img_star1,
			R.id.activity_movie_details_img_star2,
			R.id.activity_movie_details_img_star3,
			R.id.activity_movie_details_img_star4,
			R.id.activity_movie_details_img_star5
	})
	List<ImageView> mIvVotes;
	@BindView(R.id.activity_movie_details_tv_genres)
	TextView mTvGenres;
	@BindView(R.id.activity_movie_details_tv_overview)
	TextView mTvOverview;
	@BindView(R.id.activity_movie_details_img_poster_belongs)
	ImageView mIvPosterBelongs;
	@BindView(R.id.activity_movie_details_tv_title_belongs)
	TextView mTvTitleBelongs;
	@BindView(R.id.activity_movie_details_tv_budget)
	TextView mTvBudget;
	@BindView(R.id.activity_movie_details_tv_revenue)
	TextView mTvRevenue;
	@BindView(R.id.activity_movie_details_tv_companies)
	TextView mTvCompanies;
	@BindView(R.id.activity_movie_details_tv_countries)
	TextView mTvCountries;
	@BindView(R.id.activity_movie_details_img_star1)
	ImageView activityMovieDetailsImgStar1;
	@BindView(R.id.activity_movie_details_img_star2)
	ImageView activityMovieDetailsImgStar2;
	@BindView(R.id.activity_movie_details_img_star3)
	ImageView activityMovieDetailsImgStar3;
	@BindView(R.id.activity_movie_details_img_star4)
	ImageView activityMovieDetailsImgStar4;
	@BindView(R.id.activity_movie_details_img_star5)
	ImageView activityMovieDetailsImgStar5;
	@BindView(R.id.activity_movie_details_loading)
	LinearLayout mLayoutLoading;

	private ResultsModel mResultsModel;
	private Activity mActivity;
	private MovieRepository mMovieRepository;
	private boolean isFavourite = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_details);
		ButterKnife.bind(this);
		mActivity = this;
		loadIntentData();
		setupToolBar();

		ColorStateList csl = AppCompatResources.getColorStateList(this, R.color.fab_tint_state);
		Drawable drawable = DrawableCompat.wrap(mFab.getDrawable());
		DrawableCompat.setTintList(drawable, csl);
		mFab.setImageDrawable(drawable);
		mFab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isFavourite) {
					mFab.setSelected(false);
					isFavourite = false;
					getFavMovieRepository().deleteFavMovie(mResultsModel);
				} else {
					mFab.setSelected(true);
					isFavourite = true;
					getFavMovieRepository().insertFavMovie(mResultsModel);
				}
			}
		});

		mLayoutLoading.setVisibility(View.VISIBLE);
		mMovieRepository = MovieRepository.getInstance();
		mMovieRepository.getMovieDetails(mResultsModel.getId()).observe(this, new Observer<MovieModel>() {
			@Override
			public void onChanged(@Nullable MovieModel movieModel) {
				updateUI(movieModel);
			}
		});
	}

	private void loadIntentData() {
		Intent intent = getIntent();
		if (intent.hasExtra(Consts.EXTRA_MOVIE)) {
			mResultsModel = intent.getParcelableExtra(Consts.EXTRA_MOVIE);
		}
		if (intent.hasExtra(Consts.EXTRA_FAV)) {
			isFavourite = intent.getBooleanExtra(Consts.EXTRA_FAV, false);
		}

		if (intent.hasExtra(Consts.EXTRA_FAV)) {
			String url = intent.getStringExtra(Consts.EXTRA_MOVIE_URL);
			if (!TextUtils.isEmpty(url)) {
				GlideLoader.url(this)
						.load(url)
						.into(mIvPoster);
			}
		}
	}

	/**
	 * Set up the {@link Toolbar}.
	 */
	private void setupToolBar() {
		if (mToolbar != null) {
			setSupportActionBar(mToolbar);
			ActionBar actionBar = getSupportActionBar();
			if (actionBar != null) {
				actionBar.setDisplayHomeAsUpEnabled(true);
			}
		}

		mCollapsingToolbar.setTitle(mResultsModel.getTitle());
		String imageUrl = MyMoviesApp.getInstance().getImageBackDropBaseUrl() + mResultsModel.getBackdropPath();
		GlideLoader.url(mActivity).load(imageUrl).into(mIvHeader);
		mAppbarlayout.setExpanded(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				supportFinishAfterTransition();
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateUI(MovieModel movieModel) {

		mFab.setSelected(isFavourite);

		String imageUrl = MyMoviesApp.getInstance().getImagePosterBaseUrl() + movieModel.getPoster_path();
		GlideLoader.url(this)
				.load(imageUrl)
				.into(mIvPoster);


		mTvTitle.setText(movieModel.getTitle());
		mTvBudget.setText("$ " + NumberFormat.getIntegerInstance().format(movieModel.getBudget()));
		mTvOverview.setText(movieModel.getOverview());
		mTvReleaseDate.setText(movieModel.getRelease_date());
		mTvRevenue.setText("$ " + NumberFormat.getIntegerInstance().format(movieModel.getRevenue()));
		mTvVote.setText(movieModel.getVote_average() + "");


		double userRating = movieModel.getVote_average() / 2;
		int integerPart = (int) userRating;

		// Fill stars
		for (int i = 0; i < integerPart; i++) {
			mIvVotes.get(i).setImageResource(R.drawable.ic_favorite_red_24dp);
		}

		// Fill half star
		if (Math.round(userRating) > integerPart) {
			mIvVotes.get(integerPart).setImageResource(R.drawable.ic_favorite_red_24dp);
		}


		int size = 0;
		String genres = "";
		size = movieModel.getGenres().size();
		for (int i = 0; i < size; i++) {
			genres += "√ " + movieModel.getGenres().get(i).getName() + (i + 1 < size ? "\n" : "");
		}
		mTvGenres.setText(genres);


		if (movieModel.getBelongs_to_collection() != null) {
			GlideLoader.url(this)
					.load(MyMoviesApp.getInstance().getImageBaseUrl() + "w92" + movieModel.getBelongs_to_collection().getPoster_path())
					.into(mIvPosterBelongs);

			mTvTitleBelongs.setText(movieModel.getBelongs_to_collection().getName());
		}


		String companies = "";
		size = movieModel.getProduction_companies().size();
		for (int i = 0; i < size; i++) {
			companies += "√ " + movieModel.getProduction_companies().get(i).getName() + (i + 1 < size ? "\n" : "");
		}
		mTvCompanies.setText(companies);

		String countries = "";
		size = movieModel.getProduction_countries().size();
		for (int i = 0; i < size; i++) {
			countries += "√ " + movieModel.getProduction_countries().get(i).getName() + (i + 1 < size ? "\n" : "");
		}
		mTvCountries.setText(countries);


		mLayoutLoading.setVisibility(View.GONE);
	}

}
