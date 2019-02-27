package com.nishant.mymovies.adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nishant.mymovies.R;
import com.nishant.mymovies.application.MyMoviesApp;
import com.nishant.mymovies.interfaces.RecyclerViewItemCheckedListener;
import com.nishant.mymovies.interfaces.RecyclerViewItemClickListener;
import com.nishant.mymovies.model.ResultsModel;
import com.nishant.mymovies.utils.GlideLoader;
import com.nishant.mymovies.utils.NetworkState;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Created by nishant on 26/02/19.
 */
public class MoviePagedListAdapter extends PagedListAdapter<ResultsModel, RecyclerView.ViewHolder> {

	private static final int TYPE_PROGRESS = 0;
	private static final int TYPE_ITEM = 1;

	private Context context;
	private NetworkState networkState;
	private RecyclerViewItemClickListener mRecyclerViewItemClickListener;
	private RecyclerViewItemCheckedListener mRecyclerViewItemCheckedListener;
	private ArrayList<Integer> mFavMoviesList;

	/*
	 * The DiffUtil is defined in the constructor
	 */
	public MoviePagedListAdapter(Context context, ArrayList<Integer> favMoviesList, RecyclerViewItemClickListener itemClickListener,
	                             RecyclerViewItemCheckedListener itemCheckedListener) {
		super(ResultsModel.DIFF_CALLBACK);
		this.context = context;
		this.mFavMoviesList = favMoviesList;
		this.mRecyclerViewItemClickListener = itemClickListener;
		this.mRecyclerViewItemCheckedListener = itemCheckedListener;
	}


	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		if (viewType == TYPE_PROGRESS) {
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_load_more, parent, false);
			return new NetworkStateItemViewHolder(v);
		} else {
			View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_grid, parent, false);
			return new TopRatedMoviesViewHolder(itemView);
		}
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof TopRatedMoviesViewHolder) {
			TopRatedMoviesViewHolder topRatedMoviesViewHolder = (TopRatedMoviesViewHolder) holder;
			ResultsModel resultsModel = getItem(position);
			topRatedMoviesViewHolder.position = position;
			topRatedMoviesViewHolder.mTvTitle.setText(resultsModel.getTitle());

			if (mFavMoviesList.contains(resultsModel.getId())) {
				topRatedMoviesViewHolder.mCboxFav.setChecked(true);
			} else {
				topRatedMoviesViewHolder.mCboxFav.setChecked(false);
			}

			String imageUrl = MyMoviesApp.getInstance().getImagePosterBaseUrl() + resultsModel.getPosterPath();
			Log.i("Nish", "ImageUrl : " + imageUrl);
			if (resultsModel.getPosterPath() != null) {
				GlideLoader.url(context).load(imageUrl).into(topRatedMoviesViewHolder.mIvImage);
			} else {
				topRatedMoviesViewHolder.mIvImage.setImageResource(R.drawable.ic_no_movies_small);
			}
		} else {
			((NetworkStateItemViewHolder) holder).progressBar.setIndeterminate(true);
			if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
				((NetworkStateItemViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
			} else {
				((NetworkStateItemViewHolder) holder).progressBar.setVisibility(View.GONE);
			}

			if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
				((NetworkStateItemViewHolder) holder).tvError.setVisibility(View.VISIBLE);
				((NetworkStateItemViewHolder) holder).tvError.setText(networkState.getMsg());
			} else {
				((NetworkStateItemViewHolder) holder).tvError.setVisibility(View.GONE);
			}
		}
	}

	/*
	 * Default method of RecyclerView.Adapter
	 */
	@Override
	public int getItemViewType(int position) {
		if (hasExtraRow() && position == getItemCount() - 1) {
			return TYPE_PROGRESS;
		} else {
			return TYPE_ITEM;
		}
	}

	private boolean hasExtraRow() {
		if (networkState != null && networkState != NetworkState.LOADED) {
			return true;
		} else {
			return false;
		}
	}

	public void updateFavMoviesList(ArrayList<Integer> favMoviesList) {
		mFavMoviesList = favMoviesList;
	}

	public void setNetworkState(NetworkState newNetworkState) {
		NetworkState previousState = this.networkState;
		boolean previousExtraRow = hasExtraRow();
		this.networkState = newNetworkState;
		boolean newExtraRow = hasExtraRow();
		if (previousExtraRow != newExtraRow) {
			if (previousExtraRow) {
				notifyItemRemoved(getItemCount());
			} else {
				notifyItemInserted(getItemCount());
			}
		} else if (newExtraRow && previousState != newNetworkState) {
			notifyItemChanged(getItemCount() - 1);
		}
	}

	public static class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.layout_load_more_progress)
		public ProgressBar progressBar;
		@BindView(R.id.layout_load_more_error)
		public TextView tvError;

		public NetworkStateItemViewHolder(View v) {
			super(v);
			ButterKnife.bind(this, v);
			tvError.setVisibility(View.GONE);
		}
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
