package com.aac.andcun.themoviedb_mvvm.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.aac.andcun.themoviedb_mvvm.api.ApiResponse;
import com.aac.andcun.themoviedb_mvvm.vo.Id;
import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResponse;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResult;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by andani on 22.09.2017.
 */

public abstract class FetchNextPageTask<T extends Id> implements Runnable {

    private MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();

    @Override
    public void run() {
        PaginationResult paginationResult = loadPaginationResultFromDb();
        if (paginationResult == null) {
            liveData.postValue(null);
            return;
        }
        Integer nextPage = paginationResult.next;
        if (nextPage == null) {
            liveData.postValue(Resource.success(false));
            return;
        }
        try {
            Response<PaginationResponse<T>> response = createCall(paginationResult.next).execute();
            ApiResponse<PaginationResponse<T>> apiResponse = new ApiResponse<>(response);
            if (apiResponse.isSuccessful() && apiResponse.body != null) {
                List<Integer> movieIds = new ArrayList<>();
                movieIds.addAll(paginationResult.ids);
                movieIds.addAll(apiResponse.body.getMovieIds());

                PaginationResult newPaginationResult = new PaginationResult(paginationResult.type, movieIds,
                        apiResponse.body.getTotalResults(), apiResponse.body.getNextPage());

                saveCallResult(newPaginationResult, apiResponse.body.getResults());
                liveData.postValue(Resource.success(apiResponse.body.getNextPage() != null));
            } else {
                liveData.postValue(Resource.error(apiResponse.errorMessage, true));
            }
        } catch (Exception e) {
            liveData.postValue(Resource.error(e.getMessage(), true));
        }
    }

    LiveData<Resource<Boolean>> getLiveData() {
        return liveData;
    }

    abstract PaginationResult loadPaginationResultFromDb();

    abstract Call<PaginationResponse<T>> createCall(Integer nextPage);

    abstract void saveCallResult(PaginationResult paginationResult, List<T> newData);

}