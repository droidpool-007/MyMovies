package com.nishant.mymovies.view;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nishant.mymovies.R;
import com.nishant.mymovies.adapters.MoviesFragmentPagerAdapter;
import com.nishant.mymovies.apis.MovieServiceGenerator;
import com.nishant.mymovies.apis.MoviesService;
import com.nishant.mymovies.application.MyMoviesApp;
import com.nishant.mymovies.model.ConfigurationModel;
import com.nishant.mymovies.utils.Consts;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeActivity extends BaseActivity {

	@BindView(R.id.activity_home_tabs)
	TabLayout mTabLayout;
	@BindView(R.id.activity_home_viewpager)
	ViewPager mViewpager;

	private MoviesFragmentPagerAdapter mAdapter;
	private TopRatedMoviesFragment mTopRatedMoviesFragment;
	private FavouriteMoviesFragment mFavouriteMoviesFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ButterKnife.bind(this);

		Toolbar toolbar = findViewById(R.id.activity_home_toolbar);
		setSupportActionBar(toolbar);

		if (MyMoviesApp.getInstance().getConfigurationModel() == null) {
			getConfig();
		}
	}

	@Override
	protected void onResume() {
		checkForPermissions();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
		final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				searchView.clearFocus();
				mTopRatedMoviesFragment.setSearchQuery(query);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

		searchView.setOnCloseListener(() -> {
			mTopRatedMoviesFragment.resetSearchQuery();

			return false;
		});
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Checking for permissions.
	 */
	private void checkForPermissions() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			permissionsGranted();
		} else {
			int permissionWrite = checkSelfPermission(WRITE_EXTERNAL_STORAGE);
			if (permissionWrite == PackageManager.PERMISSION_GRANTED) {
				permissionsGranted();
			} else {
				List<String> listPermissionsNeeded = new ArrayList<>();
				listPermissionsNeeded.add(WRITE_EXTERNAL_STORAGE);
				if (!listPermissionsNeeded.isEmpty()) {
					requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), Consts.REQUEST_ID_MULTIPLE_PERMISSIONS);
				}
			}
		}
	}

	/**
	 * Callback received when a permissions request has been completed.
	 */
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == Consts.REQUEST_WRITE_EXTERNAL_STORAGE) {
			if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				permissionsGranted();
			}
		}
	}

	private void permissionsGranted() {
		init();
	}

	private void init() {
		if (mViewpager != null) {
			setupViewPager();
			mTabLayout.setupWithViewPager(mViewpager);
			mTabLayout.getTabAt(0).select();
		}
	}

	private void setupViewPager() {
		mAdapter = new MoviesFragmentPagerAdapter(HomeActivity.this, getSupportFragmentManager());
		mTopRatedMoviesFragment = TopRatedMoviesFragment.newInstance();
		mFavouriteMoviesFragment = FavouriteMoviesFragment.newInstance();
		mAdapter.addFragment(mTopRatedMoviesFragment, "Top Rated");
		mAdapter.addFragment(mFavouriteMoviesFragment, "Favourites");
		mViewpager.setAdapter(mAdapter);

		mViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

		mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
//					switch (tab.getPosition()) {
//						case 0:
//							break;
//						case 1:
//							break;
//					}
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {
//				switch (tab.getPosition()) {
//					case 0:
//						break;
//					case 1:
//						break;
//				}
			}
		});
	}

	public void getConfig() {
		MoviesService moviesService = MovieServiceGenerator.createService(MoviesService.class);
		Call<ConfigurationModel> call = moviesService.getConfiguration(MyMoviesApp.getInstance().getApiKey());

		call.enqueue(new Callback<ConfigurationModel>() {
			@Override
			public void onResponse(Call<ConfigurationModel> call, Response<ConfigurationModel> response) {
				if (response.isSuccessful()) {
					ConfigurationModel configurationModel = response.body();
					if (configurationModel != null) {
						MyMoviesApp.getInstance().setConfigurationModel(configurationModel);
						init();
					} else {
						showErrorDialog("Error", "Something went wrong: List is null");
					}
				} else {
					if (response.body() != null) {
						showErrorDialog("Error", "Something went wrong: " + response.body());
					} else {
						showErrorDialog("Error", "Something went wrong: " + response.message());
					}
				}
			}

			@Override
			public void onFailure(Call<ConfigurationModel> call, Throwable t) {
				if (t instanceof SocketTimeoutException || t instanceof IOException) {
					showErrorDialog("Network Error", "No Network. Please check connection");
				} else {
					showErrorDialog("Error", "Something went wrong : " + t.getMessage());
					Log.e("ERROR", "onFailure: Something went wrong", t);
				}
			}
		});
	}
}
