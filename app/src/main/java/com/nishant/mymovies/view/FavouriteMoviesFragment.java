package com.nishant.mymovies.view;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nishant.mymovies.R;
import com.nishant.mymovies.adapters.MoviesRecyclerViewAdapter;
import com.nishant.mymovies.customviews.EmptyRecyclerView;
import com.nishant.mymovies.db.FavMovieEntity;
import com.nishant.mymovies.interfaces.OnLoadMoreListener;
import com.nishant.mymovies.interfaces.RecyclerViewItemCheckedListener;
import com.nishant.mymovies.interfaces.RecyclerViewItemClickListener;
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
	private ArrayList<Integer> mFavMoviesList = new ArrayList<>();
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

		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
		mRecyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.item_offset));
		updateUI();
		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mUnbinder.unbind();
	}

	private void updateUI() {
		if (getFavMovieRepository() != null) {

			getFavMovieRepository().getAllFavMoviesIds().observe(this, new Observer<List<Integer>>() {
				@Override
				public void onChanged(@Nullable List<Integer> favMovieIds) {
					Log.i("Nish", "FavouriteMoviesFragment : Id Change Received");
					mFavMoviesList.clear();
					mFavMoviesList.addAll(favMovieIds);
					if (mMoviesRecyclerViewAdapter != null) {
						mMoviesRecyclerViewAdapter.updateFavMoviesList(mFavMoviesList);
					}
				}
			});

			getFavMovieRepository().getAllFavMovies().observe(this, new Observer<List<FavMovieEntity>>() {
				@Override
				public void onChanged(@Nullable List<FavMovieEntity> favMovieEntities) {
					if (mMoviesRecyclerViewAdapter == null) {
						mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(getActivity(), mRecyclerView,
								favMovieEntities, mFavMoviesList, FavouriteMoviesFragment.this,
								FavouriteMoviesFragment.this, null);

						mRecyclerView.setAdapter(mMoviesRecyclerViewAdapter);
						mMoviesRecyclerViewAdapter.removeItem(null);
						mRecyclerView.setEmptyView(mEmptyView);
					} else {
						if (favMovieEntities != null) {
							mMoviesRecyclerViewAdapter.updateFavMoviesList(mFavMoviesList);
							mMoviesRecyclerViewAdapter.removeItem(null);
							mMoviesRecyclerViewAdapter.resetItems(favMovieEntities);
						}
					}

					if (favMovieEntities.isEmpty()) {
						mMoviesRecyclerViewAdapter.removeItem(null);
					}
				}
			});
		}
	}

	@Override
	public void onLoadMore(int currentPage, int totalItemCount) {
		//TODO nothing as of now.
	}

	@Override
	public void OnRecyclerViewItemClick(View view, Object object) {
		if (object instanceof FavMovieEntity) {

		}
	}

	@Override
	public void OnRecyclerViewItemChecked(boolean isChecked, Object object) {
		if (object instanceof FavMovieEntity) {
			if (!isChecked) {
				getFavMovieRepository().deleteFavMovie((FavMovieEntity) object);
				updateUI();
			}
		}
	}

}
