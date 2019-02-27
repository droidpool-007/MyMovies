package com.nishant.mymovies.customviews;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;

/*
 * Created by nishant on 26/01/17.
 */

public class EmptyRecyclerView extends RecyclerView {

	private static final int INVALID_POINTER = -1;
	private View emptyView;
	final private AdapterDataObserver observer = new AdapterDataObserver() {
		@Override
		public void onChanged() {
			checkIfEmpty();
		}

		@Override
		public void onItemRangeInserted(int positionStart, int itemCount) {
			checkIfEmpty();
		}

		@Override
		public void onItemRangeRemoved(int positionStart, int itemCount) {
			checkIfEmpty();
		}
	};

	private int mScrollPointerId = INVALID_POINTER;
	private int mInitialTouchX, mInitialTouchY;
	private int mTouchSlop;

	public EmptyRecyclerView(Context context) {
		super(context);
		final ViewConfiguration vc = ViewConfiguration.get(getContext());
		mTouchSlop = vc.getScaledTouchSlop();
	}

	public EmptyRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setScrollingTouchSlop(int slopConstant) {
		super.setScrollingTouchSlop(slopConstant);
		final ViewConfiguration vc = ViewConfiguration.get(getContext());
		switch (slopConstant) {
			case TOUCH_SLOP_DEFAULT:
				mTouchSlop = vc.getScaledTouchSlop();
				break;
			case TOUCH_SLOP_PAGING:
				mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(vc);
				break;
			default:
				break;
		}
	}

	void checkIfEmpty() {
		if (emptyView != null && getAdapter() != null) {
			final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
			emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
			setVisibility(emptyViewVisible ? GONE : VISIBLE);
		} else {
			setVisibility(VISIBLE);
		}
	}

	@Override
	public void setAdapter(Adapter adapter) {
		final Adapter oldAdapter = getAdapter();
		if (oldAdapter != null) {
			oldAdapter.unregisterAdapterDataObserver(observer);
		}
		super.setAdapter(adapter);
		if (adapter != null) {
			adapter.registerAdapterDataObserver(observer);
		}

		checkIfEmpty();


//        addOnScrollListener(new OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (shouldHideFabOnScroll && mFab != null) {
//                    if (dy > 0) {
//                        mFab.hide();
//                    } else {
//                        mFab.show();
//                    }
//                }
//            }
//        });
	}

	public void setEmptyView(View emptyView) {
		this.emptyView = emptyView;
		checkIfEmpty();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		final int action = MotionEventCompat.getActionMasked(e);
		final int actionIndex = MotionEventCompat.getActionIndex(e);

		switch (action) {
			case MotionEvent.ACTION_DOWN:
				mScrollPointerId = MotionEventCompat.getPointerId(e, 0);
				mInitialTouchX = (int) (e.getX() + 0.5f);
				mInitialTouchY = (int) (e.getY() + 0.5f);
				return super.onInterceptTouchEvent(e);

			case MotionEventCompat.ACTION_POINTER_DOWN:
				mScrollPointerId = MotionEventCompat.getPointerId(e, actionIndex);
				mInitialTouchX = (int) (MotionEventCompat.getX(e, actionIndex) + 0.5f);
				mInitialTouchY = (int) (MotionEventCompat.getY(e, actionIndex) + 0.5f);
				return super.onInterceptTouchEvent(e);

			case MotionEvent.ACTION_MOVE: {
				final int index = MotionEventCompat.findPointerIndex(e, mScrollPointerId);
				if (index < 0) {
					return false;
				}

				final int x = (int) (MotionEventCompat.getX(e, index) + 0.5f);
				final int y = (int) (MotionEventCompat.getY(e, index) + 0.5f);
				if (getScrollState() != SCROLL_STATE_DRAGGING) {
					final int dx = x - mInitialTouchX;
					final int dy = y - mInitialTouchY;
					final boolean canScrollHorizontally = getLayoutManager().canScrollHorizontally();
					final boolean canScrollVertically = getLayoutManager().canScrollVertically();
					boolean startScroll = false;
					if (canScrollHorizontally && Math.abs(dx) > mTouchSlop && (Math.abs(dx) >= Math.abs(dy) || canScrollVertically)) {
						startScroll = true;
					}
					if (canScrollVertically && Math.abs(dy) > mTouchSlop && (Math.abs(dy) >= Math.abs(dx) || canScrollHorizontally)) {
						startScroll = true;
					}
					return startScroll && super.onInterceptTouchEvent(e);
				}
				return super.onInterceptTouchEvent(e);
			}

			default:
				return super.onInterceptTouchEvent(e);
		}
	}

//	@Override
//	protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params,
//	                                               int index, int count) {
//		final LayoutManager layoutManager = getLayoutManager();
//		if (getAdapter() != null && layoutManager instanceof GridLayoutManager) {
//
//			GridLayoutAnimationController.AnimationParameters animationParams =
//					(GridLayoutAnimationController.AnimationParameters) params.layoutAnimationParameters;
//
//			if (animationParams == null) {
//				animationParams = new GridLayoutAnimationController.AnimationParameters();
//				params.layoutAnimationParameters = animationParams;
//			}
//
//			int columns = ((GridLayoutManager) getLayoutManager()).getSpanCount();
//
//			animationParams.count = count;
//			animationParams.index = index;
//			animationParams.columnsCount = columns;
//			animationParams.rowsCount = count / columns;
//
//			final int invertedIndex = count - 1 - index;
//			animationParams.column = columns - 1 - (invertedIndex % columns);
//			animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns;
//
//		} else {
//			// Proceed as normal if using another type of LayoutManager
//			super.attachLayoutAnimationParameters(child, params, index, count);
//		}
//	}
}