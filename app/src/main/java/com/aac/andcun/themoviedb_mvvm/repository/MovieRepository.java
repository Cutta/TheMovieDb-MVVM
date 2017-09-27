package com.aac.andcun.themoviedb_mvvm.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.aac.andcun.themoviedb_mvvm.api.ApiResponse;
import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.db.CreditDao;
import com.aac.andcun.themoviedb_mvvm.db.MovieDao;
import com.aac.andcun.themoviedb_mvvm.db.TMDBDb;
import com.aac.andcun.themoviedb_mvvm.util.AbsentLiveData;
import com.aac.andcun.themoviedb_mvvm.util.AppExecutors;
import com.aac.andcun.themoviedb_mvvm.vo.Cast;
import com.aac.andcun.themoviedb_mvvm.vo.Credit;
import com.aac.andcun.themoviedb_mvvm.vo.Credits;
import com.aac.andcun.themoviedb_mvvm.vo.Crew;
import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResponse;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResult;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseCredits;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Call;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

@Singleton
public class MovieRepository {

    public enum MovieListType {
        Popular,
        NowPlaying,
        UpComing,
        TopRated;

        public String getTitle() {
            switch (this) {
                case Popular:
                    return "POPULAR";
                case NowPlaying:
                    return "NOW PLAYING";
                case UpComing:
                    return "UPCOMING";
                case TopRated:
                    return "TOP RATED";
                default:
                    throw new RuntimeException();
            }
        }

        String getType() {
            return getClass().getSimpleName() + "." + name();
        }

    }

    private TMDBDb db;
    private MovieDao movieDao;
    private CreditDao creditDao;
    private TMDBService service;
    private AppExecutors appExecutors;


    @Inject
    public MovieRepository(TMDBDb db, TMDBService service, MovieDao movieDao, CreditDao creditDao, AppExecutors appExecutors) {
        this.db = db;
        this.service = service;
        this.movieDao = movieDao;
        this.creditDao = creditDao;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<Movie>>> getMovies(final MovieListType movieListType) {
        return new NetworkBoundResource<List<Movie>, PaginationResponse<Movie>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull PaginationResponse<Movie> paginationResponse) {
                List<Integer> movieIds = paginationResponse.getIds();
                PaginationResult paginationResult = new PaginationResult(movieListType.getType(), movieIds,
                        paginationResponse.getTotalResults(), paginationResponse.getNextPage());
                db.beginTransaction();
                try {
                    movieDao.insertMovies(paginationResponse.getResults());
                    movieDao.insert(paginationResult);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return Transformations.switchMap(movieDao.search(movieListType.getType()), new Function<PaginationResult, LiveData<List<Movie>>>() {
                    @Override
                    public LiveData<List<Movie>> apply(PaginationResult paginationResult) {
                        if (paginationResult == null)
                            return AbsentLiveData.create();
                        else {
                            return movieDao.loadOrdered(paginationResult.ids);
                        }
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PaginationResponse<Movie>>> createCall() {
                switch (movieListType) {
                    case Popular:
                        return service.getPopularMovies(ApiConstants.API_KEY, Locale.getDefault().getLanguage());
                    case NowPlaying:
                        return service.getNowPlayingMovies(ApiConstants.API_KEY, Locale.getDefault().getLanguage());
                    case UpComing:
                        return service.getUpcomingMovies(ApiConstants.API_KEY, Locale.getDefault().getLanguage());
                    case TopRated:
                        return service.getTopRatedMovies(ApiConstants.API_KEY, Locale.getDefault().getLanguage());
                    default:
                        throw new RuntimeException("Movie List Type is not valid");
                }
            }

        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> fetchNextPage(final MovieListType movieListType) {
        FetchNextPageTask<Movie> nextPageTask = new FetchNextPageTask<Movie>() {

            @Override
            PaginationResult loadPaginationResultFromDb() {
                return movieDao.findPaginationResult(movieListType.getType());
            }

            @Override
            Call<PaginationResponse<Movie>> createCall(Integer nextPage) {
                switch (movieListType) {
                    case Popular:
                        return service.getPopularMovies(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), nextPage);
                    case NowPlaying:
                        return service.getNowPlayingMovies(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), nextPage);
                    case UpComing:
                        return service.getUpcomingMovies(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), nextPage);
                    case TopRated:
                        return service.getTopRatedMovies(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), nextPage);
                    default:
                        throw new RuntimeException("Movie List Type is not valid");
                }
            }

            @Override
            void saveCallResult(PaginationResult paginationResult, List<Movie> newData) {
                db.beginTransaction();
                try {
                    movieDao.insertMovies(newData);
                    movieDao.insert(paginationResult);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }

        };
        appExecutors.networkIO().execute(nextPageTask);
        return nextPageTask.getLiveData();
    }

    public LiveData<Resource<Movie>> getMovie(final int movieId) {

        return new NetworkBoundResource<Movie, Movie>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull Movie item) {
                movieDao.insert(item);

            }

            @Override
            protected boolean shouldFetch(@Nullable Movie data) {
                return true; //todo
            }

            @NonNull
            @Override
            protected LiveData<Movie> loadFromDb() {
                return movieDao.loadById(movieId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Movie>> createCall() {
                return service.getMovie(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
            }
        }.asLiveData();

    }

    public Observable<Movie> getMovieDetail(int movieId) {
        return service.getMovieDetail(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
    }

    // public Observable<ResponseCredits> getCredits(int movieId) {
    //   return service.getMovieCredit(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
    //}

    public Observable<PaginationResponse> getSimilars(int movieId) {
        return service.getSimilarMovies(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
    }

    public LiveData<Resource<Credits>> getCredits(final int movieId) {
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
                return Transformations.switchMap(creditDao.find(movieId), new Function<Credit, LiveData<Credits>>() {
                    @Override
                    public LiveData<Credits> apply(Credit credit) {
                        if (credit == null)
                            return AbsentLiveData.create();
                        else
                            return creditDao.loadCredits(movieId);                     }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseCredits>> createCall() {
                return service.getCredits(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
            }
        }.asLiveData();
    }

}