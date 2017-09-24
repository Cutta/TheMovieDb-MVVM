package com.aac.andcun.themoviedb_mvvm.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.aac.andcun.themoviedb_mvvm.vo.Resource;

/**
 * Created by andani on 22.09.2017.
 */

public abstract class NextPageHandler implements Observer<Resource<Boolean>> {

    private LiveData<Resource<Boolean>> nextPageLiveData;
    private MutableLiveData<LoadMoreState> loadMoreState;
    private boolean hasMore = true;
    private boolean isRunning;

    public NextPageHandler() {
        this.loadMoreState = new MutableLiveData<>();
        reset();
    }

    public void loadNextPage() {
        if (isRunning || !hasMore)
            return;
        unregister();
        isRunning = true;
        nextPageLiveData = createNextPageCall();
        loadMoreState.setValue(new LoadMoreState(isRunning, null));
        nextPageLiveData.observeForever(this);
    }

    public MutableLiveData<LoadMoreState> getLoadMoreState() {
        return loadMoreState;
    }

    @Override
    public void onChanged(@Nullable Resource<Boolean> result) {
        if (result == null)
            reset();
        else {
            switch (result.status) {
                case SUCCESS:
                    isRunning = false;
                    hasMore = result.data;
                    loadMoreState.setValue(new LoadMoreState(false, null));
                    break;
                case ERROR:
                    isRunning = false;
                    hasMore = true;
                    loadMoreState.setValue(new LoadMoreState(false, result.message));
                    break;
            }
        }
    }

    private void reset() {
        unregister();
        hasMore = true;
        loadMoreState.setValue(new LoadMoreState(false, null));
    }

    private void unregister() {
        if (nextPageLiveData != null) {
            nextPageLiveData.removeObserver(this);
            nextPageLiveData = null;
        }
    }

    public abstract LiveData<Resource<Boolean>> createNextPageCall();

    public static class LoadMoreState {

        private boolean running;
        private String errorMessage;
        private boolean errorHandled;

        LoadMoreState(boolean running, String errorMessage) {
            this.running = running;
            this.errorMessage = errorMessage;
        }

        public boolean isRunning() {
            return running;
        }

        public String getErrorMessage() {
            if (errorHandled)
                return null;
            errorHandled = true;
            return errorMessage;
        }

    }

}