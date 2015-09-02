package com.nextfaze.poweradapters.asyncdata;

import com.nextfaze.asyncdata.Data;
import com.nextfaze.poweradapters.binding.BindingAdapter;
import com.nextfaze.poweradapters.binding.Mapper;
import lombok.NonNull;

public final class DataBindingAdapter extends BindingAdapter {

    @NonNull
    private final com.nextfaze.asyncdata.DataObserver mDataObserver = new com.nextfaze.asyncdata.DataObserver() {
        @Override
        public void onChange() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemRangeMoved(fromPosition, toPosition, itemCount);
        }
    };

    @NonNull
    private final Data<?> mData;

    public DataBindingAdapter(@NonNull Data<?> data, @NonNull Mapper mapper) {
        super(mapper);
        mData = data;
    }

    @NonNull
    @Override
    protected Object getItem(int position) {
        return mData.get(position, Data.FLAG_PRESENTATION);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    protected void onFirstObserverRegistered() {
        super.onFirstObserverRegistered();
        mData.registerDataObserver(mDataObserver);
    }

    @Override
    protected void onLastObserverUnregistered() {
        super.onLastObserverUnregistered();
        mData.unregisterDataObserver(mDataObserver);
    }
}