package com.nishant.mymovies.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by nishant on 24/02/19.
 */
public class MoviesFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragments = new ArrayList<>();
	private List<String> mFragmentTitles = new ArrayList<>();
	private Context mContext;

	public MoviesFragmentPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		mContext = context;
	}

	public void addFragment(Fragment fragment) {
		mFragments.add(fragment);
	}

	public void addFragment(Fragment fragment, String title) {
		mFragments.add(fragment);
		mFragmentTitles.add(title);
	}

	public void removeAllFragments() {
		mFragments.clear();
		mFragmentTitles.clear();
		mFragments = new ArrayList<>();
		mFragmentTitles = new ArrayList<>();
	}

	public void removeAllFragments(FragmentManager supportFragmentManager) {
		for (Fragment fragment : mFragments) {
			if (fragment != null) {
				supportFragmentManager.beginTransaction().remove(fragment).commit();
			}
		}

		mFragments.clear();
		mFragmentTitles.clear();
		mFragments = new ArrayList<>();
		mFragmentTitles = new ArrayList<>();
	}


	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (mFragmentTitles != null && !mFragmentTitles.isEmpty()) {
			return mFragmentTitles.get(position);
		}
		return null;
	}
}
