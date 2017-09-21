package com.aac.andcun.themoviedb_mvvm.api;

import android.arch.lifecycle.LiveData;

import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResponse;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseCredits;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseRequestToken;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseSessionId;

import io.reactivex.Observable;
import retrofit2.Call;
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
/*
    //TV
    @GET("tv/on_the_air")
    Observable<PaginationResponse<ResultTv>> getTvOnTheAir(@Query("api_key") String apiKey,
                                                           @Query("language") String language,
                                                           @Query("page") int page);

    @GET("tv/popular")
    Observable<PaginationResponse<ResultTv>> getPopularTv(@Query("api_key") String apiKey,
                                                          @Query("language") String language,
                                                          @Query("page") int page);

    @GET("tv/top_rated")
    Observable<PaginationResponse<ResultTv>> getTopRatedTv(@Query("api_key") String apiKey,
                                                           @Query("language") String language,
                                                           @Query("page") int page);

    @GET("tv/airing_today")
    Observable<PaginationResponse<ResultTv>> getAiringToday(@Query("api_key") String apiKey,
                                                            @Query("language") String language,
                                                            @Query("page") int page);
*/
    //FILM

    @GET("movie/popular")
    Observable<PaginationResponse> getPopularMovie(@Query("api_key") String apiKey,
                                                   @Query("language") String language,
                                                   @Query("page") int page);


    @GET("movie/popular")
    LiveData<ApiResponse<PaginationResponse>> getPopularM(@Query("api_key") String apiKey,
                                                          @Query("language") String language);

    @GET("movie/popular")
    Call<PaginationResponse> getPopularM(@Query("api_key") String apiKey,
                                         @Query("language") String language,
                                         @Query("page") int page);

    @GET("movie/top_rated")
    Observable<PaginationResponse> getTopRatedMovie(@Query("api_key") String apiKey,
                                                    @Query("language") String language,
                                                    @Query("page") int page);

    @GET("movie/upcoming")
    Observable<PaginationResponse> getUpcomingMovie(@Query("api_key") String apiKey,
                                                    @Query("language") String language,
                                                    @Query("page") int page);

    @GET("movie/now_playing")
    Observable<PaginationResponse> getNowPlayingMovie(@Query("api_key") String apiKey,
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
    Observable<PaginationResponse> getSimilarMovies(@Path("movie_id") int movieId,
                                                    @Query("api_key") String apiKey,
                                                    @Query("language") String language);

    //DISCOVER
    @GET("discover/movie")
    Observable<PaginationResponse> getDiscoverMovie(@Query("api_key") String apiKey,
                                                    @Query("language") String language,
                                                    @Query("page") int page);

//    @GET("discover/tv")
    //  Observable<PaginationResponse<ResultTv>> getDiscoverTv(@Query("api_key") String apiKey,
    //                                                       @Query("language") String language,
    //                                                     @Query("page") int page);

}
