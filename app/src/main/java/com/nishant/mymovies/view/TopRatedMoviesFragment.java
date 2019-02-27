package com.nishant.mymovies.view;

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
	private ArrayList<Integer> mFavMoviesIdList = new ArrayList<>();

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
		return rootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
		mRecyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.item_offset));
		mAdapter = new MoviePagedListAdapter(getActivity().getApplicationContext(), mFavMoviesIdList, this, this);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setEmptyView(mEmptyView);

		isLoaded = true;
		movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.i("Nish", "TopRatedMoviesFragment : onStart");
		getFavMovieRepository().getAllFavMoviesIds().observe(getViewLifecycleOwner(), favMovieIds -> {
			Log.i("Nish", "TopRatedMoviesFragment : getAllFavMoviesIds");
			mFavMoviesIdList.clear();
			mFavMoviesIdList.addAll(favMovieIds);
			if (mAdapter != null) {
				mAdapter.updateFavMoviesList(mFavMoviesIdList);
			}
		});

		movieViewModel.getResultModelLiveData().observe(getViewLifecycleOwner(), pagedList -> {
			Log.i("Nish", "TopRatedMoviesFragment : getResultModelLiveData");
			mAdapter.updateFavMoviesList(mFavMoviesIdList);
			mAdapter.submitList(pagedList);
		});

		movieViewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState -> {
			Log.i("Nish", "TopRatedMoviesFragment : getNetworkState");
			mAdapter.setNetworkState(networkState);
		});
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.i("Nish", "TopRatedMoviesFragment : onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.i("Nish", "TopRatedMoviesFragment : onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.i("Nish", "TopRatedMoviesFragment : onStop");
		Log.i("Nish", "TopRatedMoviesFragment : removeObservers");
		movieViewModel.getResultModelLiveData().removeObservers(getViewLifecycleOwner());
		movieViewModel.getNetworkState().removeObservers(getViewLifecycleOwner());
		getFavMovieRepository().getAllFavMoviesIds().removeObservers(getViewLifecycleOwner());
		super.onStop();
	}

	@Override
	public void onDestroy() {
		Log.i("Nish", "TopRatedMoviesFragment : onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.i("Nish", "TopRatedMoviesFragment : onDetach");
		super.onDetach();
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

		if (mFavMoviesIdList.contains(resultsModel.getId())) {
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
			mAdapter.updateFavMoviesList(mFavMoviesIdList);
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

		mAdapter = new MoviePagedListAdapter(getActivity().getApplicationContext(), mFavMoviesIdList, this, this);
		movieViewModel.getSearchResultModelLiveData().observe(this, pagedList -> {

			mAdapter.updateFavMoviesList(mFavMoviesIdList);
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

		mAdapter = new MoviePagedListAdapter(getActivity().getApplicationContext(), mFavMoviesIdList, this, this);
		movieViewModel.getResultModelLiveData().observe(this, pagedList -> {
			mAdapter.updateFavMoviesList(mFavMoviesIdList);
			mAdapter.submitList(pagedList);
		});

		movieViewModel.getNetworkState().observe(this, networkState -> {
			mAdapter.setNetworkState(networkState);
		});

		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setEmptyView(mEmptyView);
	}
}
