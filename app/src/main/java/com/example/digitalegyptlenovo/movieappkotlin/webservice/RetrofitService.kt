package com.example.digitalegyptlenovo.movieappkotlin.webservice

import com.example.digitalegyptlenovo.movieappkotlin.model.Genres
import com.example.digitalegyptlenovo.movieappkotlin.model.Movies
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mohamed Elshafey on 4/18/2018.
 */
interface RetrofitService {

    @GET("genre/movie/list?language=en-US&page=1")
    fun listGenres(@Query("api_key") api_key: String): Observable<Genres>

    @GET("movie/popular?language=en-US")
    fun listPopular(@Query("api_key") api_key: String, @Query("page") page: Int): Observable<Movies>

    @GET("movie/top_rated?language=en-US")
    fun listTopRated(@Query("api_key") api_key: String, @Query("page") page: Int): Observable<Movies>

    @GET("movie/now_playing?language=en-US")
    fun listNowPlaying(@Query("api_key") api_key: String, @Query("page") page: Int): Observable<Movies>
}