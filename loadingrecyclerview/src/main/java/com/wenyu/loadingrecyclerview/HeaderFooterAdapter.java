package com.wenyu.loadingrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

public class HeaderFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	public HeaderFooterAdapter(RecyclerView.Adapter adapter, LinearLayout headerView, LinearLayout footerView) {
		mInnerAdapter = adapter;
		mContainerHeader = headerView;
		mContainerFooter = footerView;
		mInnerAdapter.registerAdapterDataObserver(mDataObserver);
	}

	private Listener mListener;

	public void setListener(Listener listener) {
		mListener = listener;
	}

	/**
	 * RecyclerView使用的，真正的Adapter
	 */
	private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;
	private LinearLayout mContainerHeader;
	private LinearLayout mContainerFooter;

	private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {

		@Override
		public void onChanged() {
			super.onChanged();
			notifyDataSetChanged();
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount) {
			super.onItemRangeChanged(positionStart, itemCount);
			notifyItemRangeChanged(positionStart + getHeaderItemCount(), itemCount);
		}

		@Override
		public void onItemRangeInserted(int positionStart, int itemCount) {
			super.onItemRangeInserted(positionStart, itemCount);
			notifyItemRangeInserted(positionStart + getHeaderItemCount(), itemCount);
		}

		@Override
		public void onItemRangeRemoved(int positionStart, int itemCount) {
			super.onItemRangeRemoved(positionStart, itemCount);
			notifyItemRangeRemoved(positionStart + getHeaderItemCount(), itemCount);
		}

		@Override
		public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
			super.onItemRangeMoved(fromPosition, toPosition, itemCount);
			notifyDataSetChanged();
		}
	};

	public RecyclerView.Adapter getInnerAdapter() {
		return mInnerAdapter;
	}

	private int getHeaderItemCount() {
		return mContainerHeader == null ? 0
				: mContainerHeader.getChildCount() > 0 ? 1 : 0;
	}

	private int getFooterItemCount() {
		return mContainerFooter == null ? 0
				: mContainerFooter.getChildCount() > 0 ? 1 : 0;
	}

	@Override
	public int getItemCount() {
		return mInnerAdapter.getItemCount() + getHeaderItemCount() + getFooterItemCount();
	}

	private static final int TYPE_HEADER_VIEW = 0x100;
	private static final int TYPE_FOOTER_VIEW = 0x1000;

	@Override
	public int getItemViewType(int position) {
		if (0 <= position && position < getHeaderItemCount()) {
			return TYPE_HEADER_VIEW;
		}

		if (getHeaderItemCount() <= position && position < getHeaderItemCount() + mInnerAdapter.getItemCount()) {
			return mInnerAdapter.getItemViewType(position - getHeaderItemCount());
		}

		if (getHeaderItemCount() + mInnerAdapter.getItemCount() <= position && position < getItemCount()) {
			return TYPE_FOOTER_VIEW;
		}

		return 0;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TYPE_HEADER_VIEW) {
			return new HeaderViewHolder(mContainerHeader);
		} else if (viewType == TYPE_FOOTER_VIEW) {
			return new FooterViewHolder(mContainerFooter);
		} else {
			return mInnerAdapter.onCreateViewHolder(parent, viewType);
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
		super.onBindViewHolder(holder, position, payloads);
		if (getHeaderItemCount() <= position && position < mInnerAdapter.getItemCount() + getHeaderItemCount()) {
			mInnerAdapter.onBindViewHolder(holder, position - getHeaderItemCount(), payloads);
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (mListener != null) {
			if (holder instanceof HeaderViewHolder) {
				mListener.onBindHeaderViewHolder(holder, position);
			}
			if (holder instanceof FooterViewHolder) {
				mListener.onBindFooterViewHolder(holder, position);
			}
		}
	}

	public static class HeaderViewHolder extends RecyclerView.ViewHolder {
		public HeaderViewHolder(View itemView) {
			super(itemView);
		}
	}

	public static class FooterViewHolder extends RecyclerView.ViewHolder {
		public FooterViewHolder(View itemView) {
			super(itemView);
		}
	}

	interface Listener {
		void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position);

		void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position);
	}

}
