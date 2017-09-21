package com.aac.andcun.themoviedb_mvvm.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.aac.andcun.themoviedb_mvvm.api.ApiResponse;
import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.db.TMDBDb;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResponse;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResult;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

/**
 * Created by andani on 22.09.2017.
 */

public class FetchNextPageTask implements Runnable {

    private TMDBDb mDb;
    private TMDBService mTMDBService;
    private MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();

    public FetchNextPageTask(TMDBDb db, TMDBService tmdbService) {
        this.mDb = db;
        this.mTMDBService = tmdbService;
    }

    @Override
    public void run() {
        PaginationResult paginationResult = mDb.movieDao().findPaginationResult(MovieRepository.type);
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
            Response<PaginationResponse> response = mTMDBService.getPopularM(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), nextPage).execute();
            ApiResponse<PaginationResponse> apiResponse = new ApiResponse<>(response);
            if (apiResponse.isSuccessful() && apiResponse.body != null) {
                List<Integer> movieIds = new ArrayList<>();
                movieIds.addAll(paginationResult.ids);
                movieIds.addAll(apiResponse.body.getMovieIds());

                PaginationResult newPaginationResult = new PaginationResult(MovieRepository.type, movieIds,
                        apiResponse.body.getTotalResults(), apiResponse.body.getNextPage());

                try {
                    mDb.beginTransaction();
                    mDb.movieDao().insertMovies(apiResponse.body.getResults());
                    mDb.movieDao().insert(newPaginationResult);
                    mDb.setTransactionSuccessful();
                } finally {
                    mDb.endTransaction();
                }

                liveData.postValue(Resource.success(apiResponse.body.getNextPage() != null));
            } else {
                liveData.postValue(Resource.error(apiResponse.errorMessage, true));
            }
        } catch (Exception e) {
            liveData.setValue(Resource.error(e.getMessage(), true));
        }
    }

    LiveData<Resource<Boolean>> getLiveData() {
        return liveData;
    }

}