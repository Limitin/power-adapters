package com.nextfaze.poweradapters.binding;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import com.nextfaze.poweradapters.Holder;
import com.nextfaze.poweradapters.PowerAdapter;
import com.nextfaze.poweradapters.ViewType;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.util.WeakHashMap;

@Accessors(prefix = "m")
public abstract class BindingAdapter extends PowerAdapter {

    @NonNull
    private final WeakHashMap<ViewType, Binder> mBinders = new WeakHashMap<>();

    @NonNull
    private final Mapper mMapper;

    public BindingAdapter(@NonNull Mapper mapper) {
        mMapper = mapper;
    }

    @NonNull
    protected abstract Object getItem(int position);

    @NonNull
    @Override
    public final View newView(@NonNull ViewGroup parent, @NonNull ViewType viewType) {
        Binder binder = mBinders.get(viewType);
        if (binder == null) {
            // Should never happen, as callers are required to invoke getItemViewType(int) before invoking this method.
            throw new AssertionError("No binder associated with view type");
        }
        return binder.newView(parent);
    }

    @Override
    public final void bindView(@NonNull View view, @NonNull Holder holder) {
        int position = holder.getPosition();
        Object item = getItem(position);
        binderOrThrow(item, position).bindView(item, view, holder);
    }

    @NonNull
    @Override
    public final ViewType getItemViewType(int position) {
        Object item = getItem(position);
        Binder binder = binderOrThrow(item, position);
        ViewType viewType = binder.getViewType(item, position);
        mBinders.put(viewType, binder);
        return viewType;
    }

    @Override
    public final boolean isEnabled(int position) {
        Object item = getItem(position);
        return binderOrThrow(item, position).isEnabled(item, position);
    }

    @Override
    public final long getItemId(int position) {
        Object item = getItem(position);
        return binderOrThrow(item, position).getItemId(item, position);
    }

    @Override
    public boolean hasStableIds() {
        return mMapper.hasStableIds();
    }

    @NonNull
    private Binder binderOrThrow(@NonNull Object item, int position) {
        Binder binder = mMapper.getBinder(item, position);
        assertBinder(binder, position, item);
        return binder;
    }

    private void assertBinder(@Nullable Binder binder, int position, Object item) {
        if (binder == null) {
            throw new AssertionError("No binder for position " + position + ", item " + item);
        }
    }
}
