package com.nishant.mymovies.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nishant.mymovies.R;
import com.nishant.mymovies.application.MyMoviesApp;
import com.nishant.mymovies.customviews.AbstractRecyclerViewLoadingAdapter;
import com.nishant.mymovies.db.FavMovieEntity;
import com.nishant.mymovies.interfaces.OnLoadMoreListener;
import com.nishant.mymovies.interfaces.RecyclerViewItemCheckedListener;
import com.nishant.mymovies.interfaces.RecyclerViewItemClickListener;
import com.nishant.mymovies.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by nishant on 26/02/19.
 */
public class MoviesRecyclerViewAdapter extends AbstractRecyclerViewLoadingAdapter<FavMovieEntity> {

	private RecyclerViewItemClickListener mRecyclerViewItemClickListener;
	private RecyclerViewItemCheckedListener mRecyclerViewItemCheckedListener;
	private List<FavMovieEntity> mDataset;
	private ArrayList<Integer> mFavMoviesList;
	private Activity mActivity;

	public MoviesRecyclerViewAdapter(Activity activity, RecyclerView recyclerView, List<FavMovieEntity> items,
	                                 ArrayList<Integer> favMoviesList,
	                                 RecyclerViewItemClickListener itemClickListener,
	                                 RecyclerViewItemCheckedListener itemCheckedListener,
	                                 OnLoadMoreListener onLoadMoreListener) {
		super(recyclerView, items, onLoadMoreListener);
		this.mActivity = activity;
		this.mDataset = items;
		this.mFavMoviesList = favMoviesList;
		this.mRecyclerViewItemClickListener = itemClickListener;
		this.mRecyclerViewItemCheckedListener = itemCheckedListener;

	}

	@Override
	public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_grid, parent, false);
		return new TopRatedMoviesViewHolder(itemView);
	}

	@Override
	public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		TopRatedMoviesViewHolder topRatedMoviesViewHolder = (TopRatedMoviesViewHolder) viewHolder;

		FavMovieEntity favMovieEntity = mDataset.get(position);
		topRatedMoviesViewHolder.position = position;
		topRatedMoviesViewHolder.mTvTitle.setText(favMovieEntity.getTitle());
//		topRatedMoviesViewHolder.mIvImage.setImageDrawable(null);

		if (mFavMoviesList.contains(favMovieEntity.getId())) {
			topRatedMoviesViewHolder.mCboxFav.setChecked(true);
		} else {
			topRatedMoviesViewHolder.mCboxFav.setChecked(false);
		}

		String imageUrl = MyMoviesApp.getInstance().getImagePosterBaseUrl() + favMovieEntity.getPoster_path();
		if (favMovieEntity.getPoster_path() != null) {
			GlideLoader.url(mActivity).load(imageUrl).into(topRatedMoviesViewHolder.mIvImage);
		} else {
			topRatedMoviesViewHolder.mIvImage.setImageResource(R.drawable.ic_no_movies_small);
		}
	}

	public void updateFavMoviesList(ArrayList<Integer> favMoviesList) {
		mFavMoviesList = favMoviesList;
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	public class TopRatedMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private TextView mTvTitle;
		private ImageView mIvImage;
		private CheckBox mCboxFav;
		private int position;

		public TopRatedMoviesViewHolder(View convertView) {
			super(convertView);
			mTvTitle = convertView.findViewById(R.id.item_movie_grid_tv_title);
			mIvImage = convertView.findViewById(R.id.item_movie_grid_iv_poster);
			mCboxFav = convertView.findViewById(R.id.item_movie_grid_cbox_fav);
			mIvImage.setOnClickListener(this);

			mCboxFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					mRecyclerViewItemCheckedListener.OnRecyclerViewItemChecked(isChecked, getItem(position));
				}
			});
		}

		@Override
		public void onClick(View view) {
			mRecyclerViewItemClickListener.OnRecyclerViewItemClick(mIvImage, getItem(position));
		}
	}
}
