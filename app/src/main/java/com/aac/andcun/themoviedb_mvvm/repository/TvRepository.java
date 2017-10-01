package com.aac.andcun.themoviedb_mvvm.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.aac.andcun.themoviedb_mvvm.api.ApiResponse;
import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.db.CreditDao;
import com.aac.andcun.themoviedb_mvvm.db.TMDBDb;
import com.aac.andcun.themoviedb_mvvm.db.TvDao;
import com.aac.andcun.themoviedb_mvvm.util.AbsentLiveData;
import com.aac.andcun.themoviedb_mvvm.util.AppExecutors;
import com.aac.andcun.themoviedb_mvvm.vo.Cast;
import com.aac.andcun.themoviedb_mvvm.vo.Credit;
import com.aac.andcun.themoviedb_mvvm.vo.Credits;
import com.aac.andcun.themoviedb_mvvm.vo.Crew;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResponse;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResult;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseCredits;
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
                    return "ON THE AIR";
                case AiringToday:
                    return "AIRING TODAY";
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
    private CreditDao creditDao;
    private TMDBService service;
    private AppExecutors appExecutors;

    @Inject
    public TvRepository(TMDBDb db, TMDBService service, TvDao tvDao,CreditDao creditDao, AppExecutors appExecutors) {
        this.db = db;
        this.tvDao = tvDao;
        this.creditDao = creditDao;
        this.service = service;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<Tv>>> getTvs(final TvListType tvListType) {
        return new NetworkBoundResource<List<Tv>, PaginationResponse<Tv>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull PaginationResponse<Tv> paginationResponse) {
                List<Integer> tvIds = paginationResponse.getIds();
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

    public LiveData<Resource<Tv>> getTv(final int tvId){

        return new NetworkBoundResource<Tv, Tv>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull Tv item) {
                tvDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable Tv data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<Tv> loadFromDb() {
                return tvDao.loadById(tvId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Tv>> createCall() {
                return service.getTv(tvId,ApiConstants.API_KEY,Locale.getDefault().getLanguage());
            }
        }.asLiveData();
    }



    public LiveData<Resource<Credits>> getCredits(final int tvId) {
        return new NetworkBoundResource<Credits, ResponseCredits>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull ResponseCredits item) {
                for (Cast cast : item.getCast())
                    cast.setMovieTvId(item.getId());

                for (Crew crew : item.getCrew())
                    crew.setMovieTvId(item.getId());

                Credit credit = new Credit();
                credit.setId(item.getId());

                db.beginTransaction();

                try {
                    creditDao.insertCasts(item.getCast());
                    creditDao.insertCrews(item.getCrew());
                    creditDao.insertCredit(credit);
                    db.setTransactionSuccessful();

                } finally {
                    db.endTransaction();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable Credits data) {
                return data == null || data.credit == null;
            }

            @NonNull
            @Override
            protected LiveData<Credits> loadFromDb() {
                return Transformations.switchMap(creditDao.find(tvId), new Function<Credit, LiveData<Credits>>() {
                    @Override
                    public LiveData<Credits> apply(Credit credit) {
                        if (credit == null)
                            return AbsentLiveData.create();
                        else
                            return creditDao.loadCredits(tvId);                     }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseCredits>> createCall() {
                return service.getTvCredits(tvId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
            }
        }.asLiveData();
    }

}