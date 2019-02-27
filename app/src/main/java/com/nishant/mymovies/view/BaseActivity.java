package com.nishant.mymovies.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nishant.mymovies.repository.FavMovieRepository;
import com.nishant.mymovies.view.ErrorDialogFragment.ErrorDialogListener;


/*
 * Created by nishant on 26/02/19.
 */

public class BaseActivity extends AppCompatActivity implements ErrorDialogListener {

	protected FavMovieRepository mFavMovieRepository;
	private ErrorDialogFragment mErrorDialogFragment;
	private boolean showdialog = false;
	private ErrorDialogListener mErrorDialogListener = null;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mErrorDialogListener = null;
		mFavMovieRepository = new FavMovieRepository(this);
	}


	@Override
	protected void onStop() {
		showdialog = false;
		super.onStop();
	}

	@Override
	protected void onResumeFragments() {
		showdialog = true;
		super.onResumeFragments();
	}

	protected FavMovieRepository getFavMovieRepository() {
		return mFavMovieRepository;
	}

	public void showErrorDialog(String title, String message, ErrorDialogListener errorDialogListener) {
		try {
			if (showdialog) {
				if (errorDialogListener != null) {
					mErrorDialogListener = errorDialogListener;
					mErrorDialogFragment = ErrorDialogFragment.newInstance(title, message, mErrorDialogListener);
				} else {
					mErrorDialogFragment = ErrorDialogFragment.newInstance(title, message);
				}
				if (!isFinishing()) {
					mErrorDialogFragment.show(getSupportFragmentManager(), "ErrorDialogFragment");
				}
			} else {
				Log.d("Nish", "showErrorDialog : showdialog is false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showErrorDialog(String title, String message) {
		showErrorDialog(title, message, null);
	}

	@Override
	public void onDialogPositiveClick() {
		if (mErrorDialogFragment != null && mErrorDialogFragment.isVisible()) {
			if (mErrorDialogListener != null) {
				mErrorDialogListener.onDialogPositiveClick();
			}
			mErrorDialogFragment.dismiss();
			mErrorDialogFragment = null;
		}
	}
}
