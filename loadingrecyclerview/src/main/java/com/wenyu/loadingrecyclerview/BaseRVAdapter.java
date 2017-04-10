
package com.wenyu.loadingrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRVAdapter<VH extends BaseRVAdapter.ViewHolder, L extends BaseRVAdapter.Listener, D extends Object> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    protected List<D> mDataList = new ArrayList<>();
    private L mListener;

    public BaseRVAdapter(Context context) {
        mContext = context;
    }

    public void setDataList(List<D> dataList) {
        if (dataList != null) {
            mDataList.clear();
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void addDataList(List<D> dataList) {
        if (dataList != null) {
            int count = getItemCount();
            mDataList.addAll(dataList);
            notifyItemRangeChanged(count, dataList.size());
        }
    }

    public List<D> getDataList() {
        return mDataList;
    }

    public D getItem(int position) {
        if (mDataList == null || mDataList.size() <= 0) {
            return null;
        }
        if (position < 0 || position >= mDataList.size()) {
            return null;
        }
        return mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        holder.position = position;
        super.onBindViewHolder(holder, position, payloads);
    }

    public L getListener() {
        return mListener;
    }

    public void setListener(L listener) {
        mListener = listener;
    }

    public interface Listener {
        void onItemClicked(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getListener() != null) {
                        getListener().onItemClicked(position);
                    }
                }
            });
        }

        public final int getCurrentPosition() {
            return position;
        }
    }

}