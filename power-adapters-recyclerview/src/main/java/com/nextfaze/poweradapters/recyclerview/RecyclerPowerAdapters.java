package com.nextfaze.poweradapters.recyclerview;

import android.support.annotation.CheckResult;
import android.support.v7.widget.RecyclerView;
import com.nextfaze.poweradapters.PowerAdapter;
import lombok.NonNull;

public final class RecyclerPowerAdapters {

    private RecyclerPowerAdapters() {
    }

    @CheckResult
    @NonNull
    public static RecyclerView.Adapter<?> toRecyclerAdapter(@NonNull PowerAdapter powerAdapter) {
        return new RecyclerConverterAdapter(powerAdapter);
    }
}