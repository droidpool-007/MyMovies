package com.nishant.mymovies.view;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nishant.mymovies.R;
import com.nishant.mymovies.adapters.MoviesRecyclerViewAdapter;
import com.nishant.mymovies.application.MyMoviesApp;
import com.nishant.mymovies.customviews.EmptyRecyclerView;
import com.nishant.mymovies.db.FavMovieEntity;
import com.nishant.mymovies.interfaces.OnLoadMoreListener;
import com.nishant.mymovies.interfaces.RecyclerViewItemCheckedListener;
import com.nishant.mymovies.interfaces.RecyclerViewItemClickListener;
import com.nishant.mymovies.model.ResultsModel;
import com.nishant.mymovies.utils.Consts;
import com.nishant.mymovies.utils.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteMoviesFragment extends BaseFragment implements OnLoadMoreListener, RecyclerViewItemClickListener, RecyclerViewItemCheckedListener {

	@BindView(R.id.fragment_favourite_movies_recycler_view)
	EmptyRecyclerView mRecyclerView;
	@BindView(R.id.empty_view)
	LinearLayout mEmptyView;

	private MoviesRecyclerViewAdapter mMoviesRecyclerViewAdapter;
	private ArrayList<Integer> mFavMoviesIdList = new ArrayList<>();
	private ArrayList<FavMovieEntity> mFavMovieEntities = new ArrayList<>();
	private Unbinder mUnbinder;


	public FavouriteMoviesFragment() {
		// Required empty public constructor
	}

	public static FavouriteMoviesFragment newInstance() {
		return new FavouriteMoviesFragment();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_favourite_movies, container, false);
		mUnbinder = ButterKnife.bind(this, rootView);
		return rootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
		mRecyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.item_offset));

		mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(getActivity(), mRecyclerView,
				mFavMovieEntities, mFavMoviesIdList, FavouriteMoviesFragment.this,
				FavouriteMoviesFragment.this, null);

		mRecyclerView.setAdapter(mMoviesRecyclerViewAdapter);
		mRecyclerView.setEmptyView(mEmptyView);


		if (getFavMovieRepository() != null) {
			getFavMovieRepository().getAllFavMoviesIds().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
				@Override
				public void onChanged(@Nullable List<Integer> favMovieIds) {
					mFavMoviesIdList.clear();
					mFavMoviesIdList.addAll(favMovieIds);
					if (mMoviesRecyclerViewAdapter != null) {
						mMoviesRecyclerViewAdapter.updateFavMoviesList(mFavMoviesIdList);
					}
				}
			});

			getFavMovieRepository().getAllFavMovies().observe(getViewLifecycleOwner(), new Observer<List<FavMovieEntity>>() {
				@Override
				public void onChanged(@Nullable List<FavMovieEntity> favMovieEntities) {
					if (favMovieEntities != null) {
						mFavMovieEntities.clear();
						mFavMovieEntities.addAll(favMovieEntities);
						mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(getActivity(), mRecyclerView,
								mFavMovieEntities, mFavMoviesIdList, FavouriteMoviesFragment.this,
								FavouriteMoviesFragment.this, null);

						mRecyclerView.setAdapter(mMoviesRecyclerViewAdapter);
						mRecyclerView.setEmptyView(mEmptyView);
					}
				}
			});
		}

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		getFavMovieRepository().getAllFavMoviesIds().removeObservers(getViewLifecycleOwner());
		getFavMovieRepository().getAllFavMovies().removeObservers(getViewLifecycleOwner());
		mMoviesRecyclerViewAdapter = null;
		super.onDestroyView();
		mUnbinder.unbind();
	}

	@Override
	public void onLoadMore(int currentPage, int totalItemCount) {
		//TODO nothing as of now.
	}

	@Override
	public void OnRecyclerViewItemClick(View view, Object object) {
		if (object instanceof FavMovieEntity) {
			FavMovieEntity favMovieEntity = (FavMovieEntity) object;
			ResultsModel resultsModel = new ResultsModel();
			resultsModel.setId(favMovieEntity.getId());
			resultsModel.setTitle(favMovieEntity.getTitle());
			resultsModel.setBackdropPath(favMovieEntity.getBackdrop_path());

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
	}

	@Override
	public void OnRecyclerViewItemChecked(boolean isChecked, Object object) {
		if (object instanceof FavMovieEntity) {
			if (!isChecked) {
				getFavMovieRepository().deleteFavMovie((FavMovieEntity) object);
			}
		}
	}

}
