package com.aac.andcun.themoviedb_mvvm.api;

import com.aac.andcun.themoviedb_mvvm.vo.ResponseRequestToken;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseResultList;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseSessionId;
import com.aac.andcun.themoviedb_mvvm.vo.ResultMovie;
import com.aac.andcun.themoviedb_mvvm.vo.ResultTv;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
    Observable<ResponseResultList<ResultMovie>> getPopularMovie(@Query("api_key") String apiKey,
                                                                @Query("language") String language,
                                                                @Query("page") int page);

    @GET("movie/top_rated")
    Observable<ResponseResultList<ResultMovie>> getTopRatedMovie(@Query("api_key") String apiKey,
                                                                 @Query("language") String language,
                                                                 @Query("page") int page);

    @GET("movie/upcoming")
    Observable<ResponseResultList<ResultMovie>> getUpcomingMovie(@Query("api_key") String apiKey,
                                                                 @Query("language") String language,
                                                                 @Query("page") int page);

    @GET("movie/now_playing")
    Observable<ResponseResultList<ResultMovie>> getNowPlayingMovie(@Query("api_key") String apiKey,
                                                                   @Query("language") String language,
                                                                   @Query("page") int page);

    //DISCOVER
    @GET("discover/movie")
    Observable<ResponseResultList<ResultMovie>> getDiscoverMovie(@Query("api_key") String apiKey,
                                                                 @Query("language") String language,
                                                                 @Query("page") int page);

    @GET("discover/tv")
    Observable<ResponseResultList<ResultTv>> getDiscoverTv(@Query("api_key") String apiKey,
                                                           @Query("language") String language,
                                                           @Query("page") int page);
}
