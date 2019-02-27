package com.nishant.mymovies.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nishant.mymovies.R;
import com.nishant.mymovies.adapters.MoviePagedListAdapter;
import com.nishant.mymovies.application.MyMoviesApp;
import com.nishant.mymovies.customviews.EmptyRecyclerView;
import com.nishant.mymovies.interfaces.RecyclerViewItemCheckedListener;
import com.nishant.mymovies.interfaces.RecyclerViewItemClickListener;
import com.nishant.mymovies.model.ResultsModel;
import com.nishant.mymovies.utils.Consts;
import com.nishant.mymovies.utils.ItemOffsetDecoration;
import com.nishant.mymovies.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedMoviesFragment extends BaseFragment implements RecyclerViewItemClickListener, RecyclerViewItemCheckedListener {


	public boolean isLoaded = false;
	public boolean isSearchQuery = false;

	@BindView(R.id.fragment_top_rated_movies_recycler_view)
	EmptyRecyclerView mRecyclerView;
	@BindView(R.id.empty_view)
	LinearLayout mEmptyView;

	private List<ResultsModel> mResultsModelList = new ArrayList<>();
	private ArrayList<Integer> mFavMoviesList = new ArrayList<>();

	private Unbinder mUnbinder;
	private String searchQuery;
	private MovieViewModel movieViewModel;
	private MoviePagedListAdapter mAdapter;

	public TopRatedMoviesFragment() {
		// Required empty public constructor
	}

	public static TopRatedMoviesFragment newInstance() {
		return new TopRatedMoviesFragment();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_top_rated_movies, container, false);
		mUnbinder = ButterKnife.bind(this, rootView);

		movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
		mRecyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.item_offset));

		mAdapter = new MoviePagedListAdapter(getActivity().getApplicationContext(), mFavMoviesList, this, this);

		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setEmptyView(mEmptyView);

		isLoaded = true;
		return rootView;
	}

	@Override
	public void onResume() {
		getFavMovieRepository().getAllFavMoviesIds().observe(this, new Observer<List<Integer>>() {
			@Override
			public void onChanged(@Nullable List<Integer> favMovieIds) {
				Log.i("Nish", "TopRatedMoviesFragment : Id Change Received");
				mFavMoviesList.clear();
				mFavMoviesList.addAll(favMovieIds);
				if (mAdapter != null) {
					mAdapter.updateFavMoviesList(mFavMoviesList);
				}
			}
		});

		movieViewModel.getResultModelLiveData().observe(this, pagedList -> {
			mAdapter.updateFavMoviesList(mFavMoviesList);
			mAdapter.submitList(pagedList);
		});

		movieViewModel.getNetworkState().observe(this, networkState -> {
			mAdapter.setNetworkState(networkState);
		});

		super.onResume();
	}

	@Override
	public void onStop() {
		getFavMovieRepository().getAllFavMoviesIds().removeObservers(this);
		movieViewModel.getResultModelLiveData().removeObservers(this);
		movieViewModel.getNetworkState().removeObservers(this);
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mUnbinder.unbind();
	}


	@Override
	public void OnRecyclerViewItemClick(View view, Object object) {
		ResultsModel resultsModel = (ResultsModel) object;
		Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
		intent.putExtra(Consts.EXTRA_MOVIE, resultsModel);
		intent.putExtra(Consts.EXTRA_MOVIE_URL, MyMoviesApp.getInstance().getImagePosterBaseUrl() + resultsModel.getPosterPath());

		if (mFavMoviesList.contains(resultsModel.getId())) {
			intent.putExtra(Consts.EXTRA_FAV, true);
		} else {
			intent.putExtra(Consts.EXTRA_FAV, false);
		}
		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, "movie_poster");
		startActivityForResult(intent, Consts.REQUEST_CODE_VIEW_MOVIE, options.toBundle());
	}

	@Override
	public void OnRecyclerViewItemChecked(boolean isChecked, Object object) {
		if (object instanceof ResultsModel) {
			ResultsModel resultsModel = (ResultsModel) object;
			if (isChecked) {
				getFavMovieRepository().insertFavMovie(resultsModel);
			} else {
				getFavMovieRepository().deleteFavMovie(resultsModel);
			}
			mAdapter.updateFavMoviesList(mFavMoviesList);
		}
	}

	public void setSearchQuery(String searchQuery) {
		isSearchQuery = true;
		movieViewModel.searchMovie(searchQuery);

		if (mRecyclerView != null) {
			mRecyclerView.setAdapter(null);
			mRecyclerView.invalidate();
		} else {
			return;
		}

		mAdapter = new MoviePagedListAdapter(getActivity().getApplicationContext(), mFavMoviesList, this, this);
		movieViewModel.getSearchResultModelLiveData().observe(this, pagedList -> {
			mAdapter.updateFavMoviesList(mFavMoviesList);
			mAdapter.submitList(pagedList);
		});

		movieViewModel.getSearchNetworkState().observe(this, networkState -> {
			mAdapter.setNetworkState(networkState);
		});

		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setEmptyView(mEmptyView);
	}

	public void resetSearchQuery() {
		isSearchQuery = false;
		movieViewModel.searchMovie(searchQuery);
		movieViewModel.getSearchResultModelLiveData().removeObservers(this);
		movieViewModel.getSearchNetworkState().removeObservers(this);

		if (mRecyclerView != null) {
			mRecyclerView.setAdapter(null);
			mRecyclerView.invalidate();
		} else {
			return;
		}

		mAdapter = new MoviePagedListAdapter(getActivity().getApplicationContext(), mFavMoviesList, this, this);
		movieViewModel.getResultModelLiveData().observe(this, pagedList -> {
			mAdapter.updateFavMoviesList(mFavMoviesList);
			mAdapter.submitList(pagedList);
		});

		movieViewModel.getNetworkState().observe(this, networkState -> {
			mAdapter.setNetworkState(networkState);
		});

		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setEmptyView(mEmptyView);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Consts.REQUEST_CODE_VIEW_MOVIE) {
			//
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}