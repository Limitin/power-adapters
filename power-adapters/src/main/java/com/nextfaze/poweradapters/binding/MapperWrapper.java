package com.nextfaze.poweradapters.binding;

import android.support.annotation.Nullable;
import lombok.NonNull;

import java.util.Collection;

public class MapperWrapper implements Mapper {

    @NonNull
    protected final Mapper mMapper;

    public MapperWrapper(@NonNull Mapper mapper) {
        mMapper = mapper;
    }

    @Nullable
    @Override
    public Binder getBinder(@NonNull Object item, int position) {
        return mMapper.getBinder(item, position);
    }

    @NonNull
    @Override
    public Collection<? extends Binder> getAllBinders() {
        return mMapper.getAllBinders();
    }
}
