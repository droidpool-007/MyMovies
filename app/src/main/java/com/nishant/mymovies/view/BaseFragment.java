package com.nishant.mymovies.view;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nishant.mymovies.repository.FavMovieRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

	private Activity mActivity;

	public BaseFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof Activity) {
			if (context instanceof BaseActivity) {
				mActivity = (BaseActivity) context;
			} else {
				mActivity = (Activity) context;
			}
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (mActivity == null) {
			mActivity = activity;
		}
	}

	public void showErrorDialog(String title, String message) {
		if (mActivity instanceof BaseActivity) {
			((BaseActivity) mActivity).showErrorDialog(title, message);
		}
	}

	public FavMovieRepository getFavMovieRepository() {
		if (mActivity instanceof BaseActivity) {
			return ((BaseActivity) mActivity).getFavMovieRepository();
		}
		return null;
	}
}
