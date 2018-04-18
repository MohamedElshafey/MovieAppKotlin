package com.example.digitalegyptlenovo.movieappkotlin.webservice

import com.example.digitalegyptlenovo.movieappkotlin.model.Genres
import com.example.digitalegyptlenovo.movieappkotlin.model.Popular
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
    fun listPopularMovies(@Query("api_key") api_key: String, @Query("page") page: Int): Observable<Popular>
}