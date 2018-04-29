package com.example.digitalegyptlenovo.movieappkotlin.datamanager

import com.example.digitalegyptlenovo.movieappkotlin.database.GenreSqlHelper
import com.example.digitalegyptlenovo.movieappkotlin.model.Genres
import com.example.digitalegyptlenovo.movieappkotlin.webservice.MovieDbAPiConst
import com.example.digitalegyptlenovo.movieappkotlin.webservice.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

/**
 * Created by Mohamed Elshafey on 4/29/2018.
 */
class GenreManager(var retrofit: Retrofit, var genreSqlHelper: GenreSqlHelper) {

    private var compositeDisposable = CompositeDisposable()

    fun get(): Observable<Genres> {
        val retrofitService = retrofit.create(RetrofitService::class.java)

        val observable: Observable<Genres> = retrofitService.listGenres(MovieDbAPiConst.apiKey)

        val genresDisposable = observable.map {
            saveGenresToDB(it)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        compositeDisposable.add(genresDisposable)

        return observable
    }

    private fun saveGenresToDB(genres: Genres) {
        for (genre in genres.genres) {
            genreSqlHelper.addNewGenre(genre.id, genre.name)
        }
    }

    fun dispose() {
        compositeDisposable.dispose()
    }

}