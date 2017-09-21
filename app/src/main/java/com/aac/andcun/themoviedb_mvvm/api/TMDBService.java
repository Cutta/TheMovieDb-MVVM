package com.aac.andcun.themoviedb_mvvm.api;

import android.arch.lifecycle.LiveData;

import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseCredits;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseRequestToken;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseResultList;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseSessionId;
import com.aac.andcun.themoviedb_mvvm.vo.ResultTv;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public interface TMDBService {

    @GET("authentication/token/new")
    Observable<ResponseRequestToken> getRequestToken(@Query("api_key") String apiKey);

    @GET("authentication/session/new")
    Observable<ResponseSessionId> getSessionId(@Query("api_key") String apiKey,
                                               @Query("request_token") String requestToken);

    //TV
    @GET("tv/on_the_air")
    Observable<ResponseResultList<ResultTv>> getTvOnTheAir(@Query("api_key") String apiKey,
                                                           @Query("language") String language,
                                                           @Query("page") int page);

    @GET("tv/popular")
    Observable<ResponseResultList<ResultTv>> getPopularTv(@Query("api_key") String apiKey,
                                                          @Query("language") String language,
                                                          @Query("page") int page);

    @GET("tv/top_rated")
    Observable<ResponseResultList<ResultTv>> getTopRatedTv(@Query("api_key") String apiKey,
                                                           @Query("language") String language,
                                                           @Query("page") int page);

    @GET("tv/airing_today")
    Observable<ResponseResultList<ResultTv>> getAiringToday(@Query("api_key") String apiKey,
                                                            @Query("language") String language,
                                                            @Query("page") int page);

    //FILM

    @GET("movie/popular")
    Observable<ResponseResultList<Movie>> getPopularMovie(@Query("api_key") String apiKey,
                                                          @Query("language") String language,
                                                          @Query("page") int page);


    @GET("movie/popular")
    LiveData<ApiResponse<List<Movie>>> getPopularM(@Query("api_key") String apiKey,
                                                   @Query("language") String language,
                                                   @Query("page") int page);

    @GET("movie/top_rated")
    Observable<ResponseResultList<Movie>> getTopRatedMovie(@Query("api_key") String apiKey,
                                                           @Query("language") String language,
                                                           @Query("page") int page);

    @GET("movie/upcoming")
    Observable<ResponseResultList<Movie>> getUpcomingMovie(@Query("api_key") String apiKey,
                                                           @Query("language") String language,
                                                           @Query("page") int page);

    @GET("movie/now_playing")
    Observable<ResponseResultList<Movie>> getNowPlayingMovie(@Query("api_key") String apiKey,
                                                             @Query("language") String language,
                                                             @Query("page") int page);

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieDetail(@Path("movie_id") int movieId,
                                     @Query("api_key") String apiKey,
                                     @Query("language") String language);


    @GET("movie/{movie_id}/credits")
    Observable<ResponseCredits> getMovieCredit(@Path("movie_id") int movieId,
                                               @Query("api_key") String apiKey,
                                               @Query("language") String language);

    @GET("movie/{movie_id}/similar")
    Observable<ResponseResultList<Movie>> getSimilarMovies(@Path("movie_id") int movieId,
                                                           @Query("api_key") String apiKey,
                                                           @Query("language") String language);

    //DISCOVER
    @GET("discover/movie")
    Observable<ResponseResultList<Movie>> getDiscoverMovie(@Query("api_key") String apiKey,
                                                           @Query("language") String language,
                                                           @Query("page") int page);

    @GET("discover/tv")
    Observable<ResponseResultList<ResultTv>> getDiscoverTv(@Query("api_key") String apiKey,
                                                           @Query("language") String language,
                                                           @Query("page") int page);

}
