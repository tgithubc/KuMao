package com.tgithubc.kumao.widget.banner;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by tc :)
 */
public class BannerAdapter<T> extends RecyclerView.Adapter<BannerHolder> {

    private List<T> mDatas;
    private BannerHolderCreator mHolderCreator;
    private OnBannerItemClickListener mListener;

    public BannerAdapter(BannerHolderCreator creator, List<T> data) {
        this.mHolderCreator = creator;
        this.mDatas = data;
    }

    @Override
    public BannerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mHolderCreator.createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BannerHolder holder, int position) {
        int realPosition = getRealPosition(position);
        holder.updateView(mDatas.get(realPosition));
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> mListener.onBannerItemClick(realPosition));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mHolderCreator.createItemViewType(getRealPosition(position));
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size() < 2 ? mDatas.size() : Integer.MAX_VALUE;
    }

    public List<T> getData() {
        return mDatas;
    }

    public T getItem(int position) {
        return mDatas.get(getRealPosition(position));
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void setDatas(List<T> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener listener) {
        this.mListener = listener;
    }

    private int getRealPosition(int pos) {
        if (pos >= mDatas.size()) {
            return pos % mDatas.size();
        }
        return pos;
    }
}
