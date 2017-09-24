package com.aac.andcun.themoviedb_mvvm.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.aac.andcun.themoviedb_mvvm.api.ApiResponse;
import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.db.TMDBDb;
import com.aac.andcun.themoviedb_mvvm.db.TvDao;
import com.aac.andcun.themoviedb_mvvm.util.AbsentLiveData;
import com.aac.andcun.themoviedb_mvvm.util.AppExecutors;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResponse;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResult;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;
import com.aac.andcun.themoviedb_mvvm.vo.Tv;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class TvRepository {

    public enum TvListType {
        Popular,
        TopRated,
        OnTheAir,
        AiringToday;

        public String getTitle() {
            switch (this) {
                case Popular:
                    return "POPULAR";
                case TopRated:
                    return "TOP RATED";
                case OnTheAir:
                    return "On The Air";
                case AiringToday:
                    return "Airing Today";
                default:
                    throw new RuntimeException();
            }
        }

        String getType() {
            return getClass().getSimpleName() + "." + name();
        }

    }

    private TMDBDb db;
    private TvDao tvDao;
    private TMDBService service;
    private AppExecutors appExecutors;

    @Inject
    public TvRepository(TMDBDb db, TMDBService service, TvDao tvDao, AppExecutors appExecutors) {
        this.db = db;
        this.tvDao = tvDao;
        this.service = service;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<Tv>>> loadTvs(final TvListType tvListType) {
        return new NetworkBoundResource<List<Tv>, PaginationResponse<Tv>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull PaginationResponse<Tv> paginationResponse) {
                List<Integer> tvIds = paginationResponse.getMovieIds();
                PaginationResult paginationResult = new PaginationResult(tvListType.getType(), tvIds,
                        paginationResponse.getTotalResults(), paginationResponse.getNextPage());
                db.beginTransaction();
                try {
                    tvDao.insertTvs(paginationResponse.getResults());
                    tvDao.insert(paginationResult);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Tv> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Tv>> loadFromDb() {
                return Transformations.switchMap(tvDao.search(tvListType.getType()), new Function<PaginationResult, LiveData<List<Tv>>>() {
                    @Override
                    public LiveData<List<Tv>> apply(PaginationResult paginationResult) {
                        if (paginationResult == null)
                            return AbsentLiveData.create();
                        else
                            return tvDao.loadOrdered(paginationResult.ids);
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PaginationResponse<Tv>>> createCall() {
                switch (tvListType) {
                    case Popular:
                        return service.getPopularTvs(ApiConstants.API_KEY, Locale.getDefault().getLanguage());
                    case TopRated:
                        return service.getTopRatedTvs(ApiConstants.API_KEY, Locale.getDefault().getLanguage());
                    case OnTheAir:
                        return service.getOnTheAirTvs(ApiConstants.API_KEY, Locale.getDefault().getLanguage());
                    case AiringToday:
                        return service.getAiringTodayTvs(ApiConstants.API_KEY, Locale.getDefault().getLanguage());
                    default:
                        throw new RuntimeException("Tv List Type is not valid");
                }
            }

        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> fetchNextPage(final TvListType tvListType) {
        FetchNextPageTask<Tv> nextPageTask = new FetchNextPageTask<Tv>() {

            @Override
            PaginationResult loadPaginationResultFromDb() {
                return tvDao.findPaginationResult(tvListType.getType());
            }

            @Override
            Call<PaginationResponse<Tv>> createCall(Integer nextPage) {
                switch (tvListType) {
                    case Popular:
                        return service.getPopularTvs(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), nextPage);
                    case TopRated:
                        return service.getTopRatedTvs(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), nextPage);
                    case OnTheAir:
                        return service.getOnTheAirTvs(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), nextPage);
                    case AiringToday:
                        return service.getAiringTodayTvs(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), nextPage);
                    default:
                        throw new RuntimeException("Tv List Type is not valid");
                }
            }

            @Override
            void saveCallResult(PaginationResult paginationResult, List<Tv> newData) {
                db.beginTransaction();
                try {
                    tvDao.insertTvs(newData);
                    tvDao.insert(paginationResult);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }

        };
        appExecutors.networkIO().execute(nextPageTask);
        return nextPageTask.getLiveData();
    }

}