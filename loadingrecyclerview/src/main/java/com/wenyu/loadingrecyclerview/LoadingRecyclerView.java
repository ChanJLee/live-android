package com.wenyu.loadingrecyclerview;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LoadingRecyclerView extends SwipeRefreshLayout {
	private static final String TAG = "LoadingRecyclerView";

	public LoadingRecyclerView(Context context) {
		super(context);
		init();
	}

	public LoadingRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private LoadingListener mListener;

	public void setListener(LoadingListener listener) {
		mListener = listener;
	}

	private RecyclerView mRecyclerView;
	private HeaderFooterAdapter mAdapter;

	private ViewGroup mLayoutFooterLoading;
	private View mViewReload;
	private View mViewReloadBtn;
	private OnRefreshListener mOnRefreshListener;
	private EndlessRecyclerOnScrollListener mOnScrollListener;
	private OnClickListener mReloadOnClickListener;

	private RefreshLoadingEvent mRefreshLoadingEvent = new RefreshLoadingEvent() {
		@Override
		public void onEventStart() {
			mIsLoadMoreLocked = false;
			if (mIsPerformingRefreshOnReload) {
				mReloadLoadingEvent.onEventStart();
				return;
			}

			setLoadingType(TYPE_TOP_SHOW_LOADING);
		}

		@Override
		public void onEventSuccess() {
			if (mIsPerformingRefreshOnReload) {
				mReloadLoadingEvent.onEventSuccess();
				scrollToTop();
				return;
			}
			setLoadingType(TYPE_TOP_HIDE_LOADING);
			setLoadingType(TYPE_FOOTER_HIDE);
			scrollToTop();
		}

		@Override
		public void onEventFailure() {
			if (mIsPerformingRefreshOnReload) {
				mReloadLoadingEvent.onEventFailure();
				return;
			}
			mIsLoadMoreLocked = true;
			setLoadingType(TYPE_TOP_HIDE_LOADING);
			setLoadingType(TYPE_FOOTER_SHOW_RELOAD);
			scrollToBottom();
		}

		@Override
		public void onEventNull() {
			mIsLoadMoreLocked = true;
			setLoadingType(TYPE_FOOTER_HIDE);
		}
	};

	//如果 onEventNull或者 onEventFailure，不往外部回调onLoadMore，除非重新refresh或者reload
	private boolean mIsLoadMoreLocked = false;

	private LoadMoreLoadingEvent mLoadMoreLoadingEvent = new LoadMoreLoadingEvent() {
		@Override
		public void onEventStart() {
			Log.d(TAG, "LoadMoreLoadingEvent: -------onEventStart--------------");
			setLoadingType(TYPE_FOOTER_SHOW_LOADING);
			scrollToBottom();
		}

		@Override
		public void onEventSuccess() {
			Log.d(TAG, "LoadMoreLoadingEvent: -------onEventSuccess--------------");
			setLoadingType(TYPE_FOOTER_HIDE);
		}

		@Override
		public void onEventFailure() {
			mIsLoadMoreLocked = true;
			Log.d(TAG, "LoadMoreLoadingEvent: -------onEventFailure--------------");
			setLoadingType(TYPE_FOOTER_SHOW_RELOAD);
			scrollToBottom();
		}

		@Override
		public void onEventNull() {
			mIsLoadMoreLocked = true;
			Log.d(TAG, "LoadMoreLoadingEvent: -------onEventNull--------------");
			setLoadingType(TYPE_FOOTER_HIDE);
		}
	};

	private boolean mIsPerformingRefreshOnReload = false;

	private ReloadLoadingEvent mReloadLoadingEvent = new ReloadLoadingEvent() {
		@Override
		public void onEventStart() {
			mIsLoadMoreLocked = false;
			setLoadingType(TYPE_TOP_SHOW_LOADING);
			setLoadingType(TYPE_FOOTER_HIDE);
		}

		@Override
		public void onEventSuccess() {
			mIsPerformingRefreshOnReload = false;
			setLoadingType(TYPE_TOP_HIDE_LOADING);
		}

		@Override
		public void onEventFailure() {
			mIsLoadMoreLocked = true;
			mIsPerformingRefreshOnReload = false;
			setLoadingType(TYPE_TOP_HIDE_LOADING);
			setLoadingType(TYPE_FOOTER_SHOW_RELOAD);
			scrollToBottom();
		}

		@Override
		public void onEventNull() {
			mIsLoadMoreLocked = true;
			setLoadingType(TYPE_FOOTER_HIDE);
		}

		@Override
		public void sameAsRefresh() {
			mIsPerformingRefreshOnReload = true;
			performRefresh();
		}
	};

	private void init() {
		int color = getResources().getColor(R.color.colorPrimary);
		setColorSchemeColors(color);
		mOnRefreshListener = new OnRefreshListener() {
			@Override
			public void onRefresh() {
				Log.d(TAG, "onRefresh: --------------onRefresh--------------");
				if (mListener != null) {
					mListener.onRefresh(mRefreshLoadingEvent);
				}
			}
		};
		setOnRefreshListener(mOnRefreshListener);

		//=============

		mRecyclerView = new RecyclerView(getContext());
		ViewGroup.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mRecyclerView.setLayoutParams(lp);
		LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(mLinearLayoutManager);
		mOnScrollListener = new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
			@Override
			public void onLoadMore(int page) {
				Log.d(TAG, "onLoadMore: -------onLoadMore-----page---------" + page);

				if (mListener != null && !mIsLoadMoreLocked) {
					mListener.onLoadMore(mLoadMoreLoadingEvent);
					Log.d(TAG, "onLoadMore: -------mLoadMoreLoadingEvent-----page---------" + page);
				}
			}
		};
		mRecyclerView.addOnScrollListener(mOnScrollListener);

		//==============

		mLayoutFooterLoading = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layut_footer_loading, null);
		mViewReload = mLayoutFooterLoading.findViewById(R.id.loading_footer_reload);
		mViewReloadBtn = mLayoutFooterLoading.findViewById(R.id.loading_footer_reload_btn);
		mReloadOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick: -------onClick--------------");
				if (mListener != null) {
					mListener.onReload(mReloadLoadingEvent);
				}
			}
		};
		mViewReloadBtn.setOnClickListener(mReloadOnClickListener);
		mViewReload.setVisibility(GONE);

		mCircleView = (CircleImageView) mLayoutFooterLoading.findViewById(R.id.loading_footer_progress);
		mProgress = new MaterialProgressDrawable(getContext(), mCircleView);
		mProgress.setBackgroundColor(0xFFFAFAFA);
		mProgress.setColorSchemeColors(color);
		mProgress.showArrow(false);
		mProgress.updateSizes(MaterialProgressDrawable.DEFAULT);
		mProgress.setAlpha(255);
		mCircleView.setImageDrawable(mProgress);
		mCircleView.setVisibility(GONE);

		addView(mRecyclerView);
		addFooterLoading();
	}

	private CircleImageView mCircleView;
	private MaterialProgressDrawable mProgress;
	private LinearLayout mContainerHeader;
	private LinearLayout mContainerFooter;

	public void setAdapter(RecyclerView.Adapter adapter) {
		ensureHeaderViewContainer();
		ensureFooterViewContainer();

		mAdapter = new HeaderFooterAdapter(adapter, mContainerHeader, mContainerFooter);
		mAdapter.setListener(new HeaderFooterAdapter.Listener() {
			@Override
			public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

			}

			@Override
			public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
				Log.d(TAG, "onBindFooterViewHolder: -------onBindViewHolder---------");
				if (mCircleView.getVisibility() == VISIBLE) {
					mProgress.stop();
					mCircleView.post(new Runnable() {
						@Override
						public void run() {
							mProgress.start();
						}
					});
				}
			}
		});
		mRecyclerView.setAdapter(mAdapter);
		setLoadingType(TYPE_FOOTER_HIDE);
	}

	private void ensureHeaderViewContainer() {
		if (mContainerHeader == null) {
			mContainerHeader = new LinearLayout(getContext());
			mContainerHeader.setOrientation(LinearLayout.VERTICAL);
			mContainerHeader.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		}
	}

	private void ensureFooterViewContainer() {
		if (mContainerFooter == null) {
			mContainerFooter = new LinearLayout(getContext());
			mContainerFooter.setOrientation(LinearLayout.VERTICAL);
			mContainerFooter.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		}
	}

	private void addFooterLoading() {
		if (containsFooterView(mLayoutFooterLoading)) {
			return;
		}
		ensureFooterViewContainer();
		mContainerFooter.addView(mLayoutFooterLoading, 0);
	}

	public void addHeaderView(View headerView) {
		if (containsHeaderView(headerView)) {
			return;
		}
		ensureHeaderViewContainer();
		mContainerHeader.addView(headerView);
	}

	public void addFooterView(View footerView) {
		if (containsFooterView(footerView)) {
			return;
		}
		ensureFooterViewContainer();
		mContainerFooter.addView(footerView);
	}

	public void removeHeaderView(View view) {
		if (mContainerHeader != null) {
			mContainerHeader.removeView(view);
		}
	}

	public void removeFooterView(View view) {
		if (mContainerFooter != null) {
			mContainerFooter.removeView(view);
		}
	}

	public boolean containsHeaderView(View view) {
		if (mContainerHeader != null) {
			for (int i = 0; i < mContainerHeader.getChildCount(); i++) {
				View headerView = mContainerHeader.getChildAt(i);
				if (headerView == view) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean containsFooterView(View view) {
		if (mContainerFooter != null) {
			for (int i = 0; i < mContainerFooter.getChildCount(); i++) {
				View footerView = mContainerFooter.getChildAt(i);
				if (footerView == view) {
					return true;
				}
			}
		}
		return false;
	}

	public void reset() {
		mOnScrollListener.reset();
	}

	/*
	显示顶部下拉loading效果
	 */
	public static final int TYPE_TOP_SHOW_LOADING = 0x21;
	/*
	隐藏顶部下拉loading效果
	 */
	public static final int TYPE_TOP_HIDE_LOADING = 0x22;
	/*
	显示尾部加载更多loading效果
	 */
	public static final int TYPE_FOOTER_SHOW_LOADING = 0x31;
	/*
	显示尾部重新加载效果
	 */
	public static final int TYPE_FOOTER_SHOW_RELOAD = 0x35;
	/*
	隐藏尾部所有加载效果
	 */
	public static final int TYPE_FOOTER_HIDE = 0x36;

	@IntDef({TYPE_TOP_SHOW_LOADING,
			TYPE_TOP_HIDE_LOADING,
			TYPE_FOOTER_SHOW_LOADING,
			TYPE_FOOTER_SHOW_RELOAD,
			TYPE_FOOTER_HIDE})
	@Retention(RetentionPolicy.SOURCE)
	public @interface LOADING_TYPE {
	}

	private void setLoadingType(@LOADING_TYPE int type) {
		switch (type) {
			case TYPE_TOP_SHOW_LOADING:
				setTopLoading(true);
				break;

			case TYPE_TOP_HIDE_LOADING:
				setTopLoading(false);
				break;

			case TYPE_FOOTER_SHOW_LOADING:
				mCircleView.setVisibility(VISIBLE);
				mViewReload.setVisibility(GONE);
				mLayoutFooterLoading.setVisibility(VISIBLE);
				mProgress.stop();
				mCircleView.post(new Runnable() {
					@Override
					public void run() {
						mProgress.start();
					}
				});
				break;

			case TYPE_FOOTER_SHOW_RELOAD:
				mCircleView.setVisibility(GONE);
				mProgress.stop();
				mViewReload.setVisibility(VISIBLE);
				mLayoutFooterLoading.setVisibility(VISIBLE);
				break;

			case TYPE_FOOTER_HIDE:
				mCircleView.setVisibility(GONE);
				mProgress.stop();
				mViewReload.setVisibility(GONE);
				mLayoutFooterLoading.setVisibility(GONE);
				break;
		}
	}

	private void setTopLoading(final boolean visible) {
		post(new Runnable() {

			@Override
			public void run() {
				setRefreshing(visible);
			}
		});
	}

	public void scrollToBottom() {
		mRecyclerView.post(new Runnable() {
			@Override
			public void run() {
				mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
			}
		});
	}

	private void scrollToTop() {
		mRecyclerView.post(new Runnable() {
			@Override
			public void run() {
				mRecyclerView.smoothScrollToPosition(0);
			}
		});
	}



	/*=========*/

	public RecyclerView getView() {
		return mRecyclerView;
	}

	public void performRefresh() {
		if (mListener != null) {
			mListener.onRefresh(mRefreshLoadingEvent);
		}
	}

	public void performLoadMore() {
		if (mListener != null) {
			mListener.onLoadMore(mLoadMoreLoadingEvent);
		}
	}

	public void performReload() {
		if (mListener != null) {
			mListener.onReload(mReloadLoadingEvent);
		}
	}

	public void setRefreshEnabled(boolean enabled) {
		setEnabled(enabled);
	}



	/*==================*/

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mRecyclerView.measure(widthMeasureSpec, heightMeasureSpec);

		int height = Math.max(ViewCompat.getMinimumHeight(this), mRecyclerView.getMeasuredHeight());

		setMeasuredDimension(resolveSize(MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec), height);
	}

	public LinearLayoutManager getLayoutManager() {
		return (LinearLayoutManager) mRecyclerView.getLayoutManager();
	}
}
