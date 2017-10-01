package com.aac.andcun.themoviedb_mvvm.api;

import android.arch.lifecycle.LiveData;

import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResponse;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseCredits;
import com.aac.andcun.themoviedb_mvvm.vo.Person;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseRequestToken;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseSessionId;
import com.aac.andcun.themoviedb_mvvm.vo.Tv;

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
    //TV

    @GET("tv/on_the_air")
    LiveData<ApiResponse<PaginationResponse<Tv>>> getOnTheAirTvs(@Query("api_key") String apiKey,
                                                                 @Query("language") String language);

    @GET("tv/on_the_air")
    Call<PaginationResponse<Tv>> getOnTheAirTvs(@Query("api_key") String apiKey,
                                                @Query("language") String language,
                                                @Query("page") int page);

    //todo cuneyt livedata ve call dönüyor bak

    @GET("tv/popular")
    LiveData<ApiResponse<PaginationResponse<Tv>>> getPopularTvs(@Query("api_key") String apiKey,
                                                                @Query("language") String language);

    @GET("tv/popular")
    Call<PaginationResponse<Tv>> getPopularTvs(@Query("api_key") String apiKey,
                                               @Query("language") String language,
                                               @Query("page") int page);

    @GET("tv/top_rated")
    LiveData<ApiResponse<PaginationResponse<Tv>>> getTopRatedTvs(@Query("api_key") String apiKey,
                                                                 @Query("language") String language);

    @GET("tv/top_rated")
    Call<PaginationResponse<Tv>> getTopRatedTvs(@Query("api_key") String apiKey,
                                                @Query("language") String language,
                                                @Query("page") int page);

    @GET("tv/airing_today")
    LiveData<ApiResponse<PaginationResponse<Tv>>> getAiringTodayTvs(@Query("api_key") String apiKey,
                                                                    @Query("language") String language);

    @GET("tv/airing_today")
    Call<PaginationResponse<Tv>> getAiringTodayTvs(@Query("api_key") String apiKey,
                                                   @Query("language") String language,
                                                   @Query("page") int page);

    //FILM

    @GET("movie/popular")
    LiveData<ApiResponse<PaginationResponse<Movie>>> getPopularMovies(@Query("api_key") String apiKey,
                                                                      @Query("language") String language);

    @GET("movie/popular")
    Call<PaginationResponse<Movie>> getPopularMovies(@Query("api_key") String apiKey,
                                                     @Query("language") String language,
                                                     @Query("page") int page);

    @GET("movie/now_playing")
    LiveData<ApiResponse<PaginationResponse<Movie>>> getNowPlayingMovies(@Query("api_key") String apiKey,
                                                                         @Query("language") String language);

    @GET("movie/now_playing")
    Call<PaginationResponse<Movie>> getNowPlayingMovies(@Query("api_key") String apiKey,
                                                        @Query("language") String language,
                                                        @Query("page") int page);

    @GET("movie/upcoming")
    LiveData<ApiResponse<PaginationResponse<Movie>>> getUpcomingMovies(@Query("api_key") String apiKey,
                                                                       @Query("language") String language);

    @GET("movie/upcoming")
    Call<PaginationResponse<Movie>> getUpcomingMovies(@Query("api_key") String apiKey,
                                                      @Query("language") String language,
                                                      @Query("page") int page);

    @GET("movie/top_rated")
    LiveData<ApiResponse<PaginationResponse<Movie>>> getTopRatedMovies(@Query("api_key") String apiKey,
                                                                       @Query("language") String language);

    @GET("movie/top_rated")
    Call<PaginationResponse<Movie>> getTopRatedMovies(@Query("api_key") String apiKey,
                                                      @Query("language") String language,
                                                      @Query("page") int page);

    @GET("movie/{movie_id}")
    LiveData<ApiResponse<Movie>> getMovie(@Path("movie_id") int movieId,
                                          @Query("api_key") String apiKey,
                                          @Query("language") String language);


    @GET("tv/{tv_id}")
    LiveData<ApiResponse<Tv>> getTv(@Path("tv_id") int tvId,
                                          @Query("api_key") String apiKey,
                                          @Query("language") String language);


    @GET("person/{person_id}")
    LiveData<ApiResponse<Person>> getPerson(@Path("person_id") int personId,
                                            @Query("api_key") String apiKey,
                                            @Query("language") String language);

    //


    @GET("movie/{movie_id}/credits")
    LiveData<ApiResponse<ResponseCredits>> getMovieCredits(@Path("movie_id") int movieId,
                                                      @Query("api_key") String apiKey,
                                                      @Query("language") String language);


    @GET("tv/{tv_id}/credits")
    LiveData<ApiResponse<ResponseCredits>> getTvCredits(@Path("tv_id") int movieId,
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
    //  Observable<PaginationResponse<Tv>> getDiscoverTv(@Query("api_key") String apiKey,
    //                                                       @Query("language") String language,
    //                                                     @Query("page") int page);

}
