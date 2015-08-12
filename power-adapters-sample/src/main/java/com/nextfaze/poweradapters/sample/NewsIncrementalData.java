package com.nextfaze.poweradapters.sample;

import com.nextfaze.asyncdata.IncrementalArrayData;
import lombok.NonNull;

import javax.annotation.Nullable;

final class NewsIncrementalData extends IncrementalArrayData<NewsItem> {

    @NonNull
    private final NewsService mNewsService = new NewsService();

    private final int mTotal;
    private final int mIncrement;

    private volatile int mOffset;

    NewsIncrementalData() {
        this(100, 20);
    }

    NewsIncrementalData(int total, int increment) {
        mTotal = total;
        mIncrement = increment;
    }

    @Nullable
    @Override
    protected Result<? extends NewsItem> load() throws Throwable {
        int offset = mOffset;
        if (mOffset >= mTotal) {
            return null;
        }
        mOffset = offset + mIncrement;
        return new Result<>(mNewsService.getNews(offset, mIncrement), mTotal - mOffset);
    }

    @Override
    protected void onInvalidate() {
        mOffset = 0;
    }
}
