package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.app.Activity
import android.databinding.BaseObservable
import android.util.Log
import android.view.View
import com.example.digitalegyptlenovo.movieappkotlin.adapter.AllMovieAdapter
import com.example.digitalegyptlenovo.movieappkotlin.databinding.ActivityHomeBinding
import com.example.digitalegyptlenovo.movieappkotlin.model.Genres
import com.example.digitalegyptlenovo.movieappkotlin.model.Popular
import com.example.digitalegyptlenovo.movieappkotlin.sqlite.helper.GenreSqlHelper
import com.example.digitalegyptlenovo.movieappkotlin.webservice.MovieDbAPiConst
import com.example.digitalegyptlenovo.movieappkotlin.webservice.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

/**
 * Created by Mohamed Elshafey on 4/17/2018.
 */
class HomeViewModel(val activity: Activity, val binding: ActivityHomeBinding, val retrofit: Retrofit) : BaseObservable() {


    init {
        val genreSqlHelper = GenreSqlHelper(activity);

        if (genreSqlHelper.isTableEmpty())
            getGenresThenGetPopularMovies()
        else
            getPopularMovies()
    }

    private fun getPopularMovies() {
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val observable = retrofitService.listPopularMovies(MovieDbAPiConst.apiKey, 1)
        disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    val movieAdapter = AllMovieAdapter(activity, it.results)
                    binding.gridview.adapter = movieAdapter
                    binding.progressBar.visibility = View.GONE
                })
    }

    private var disposable: Disposable? = null

    private fun getGenresThenGetPopularMovies() {
        Log.d("HOMEVIEW_MODEL", "getGenresThenGetPopularMovies: ")


        val retrofitService = retrofit.create(RetrofitService::class.java)

        val observable: Observable<Genres> = retrofitService.listGenres(MovieDbAPiConst.apiKey)

        disposable = observable.map {
            saveGenres(it)
            return@map retrofitService.listPopularMovies(MovieDbAPiConst.apiKey, 1)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.doOnNext({
                        val movieAdapter = AllMovieAdapter(activity, it.results)
                        binding.gridview.adapter = movieAdapter
                        binding.progressBar.visibility = View.GONE
                    })

                }
                )

    }


    fun saveGenres(genres: Genres) {
        val genreSqlHelper = GenreSqlHelper(activity)
        for (genre in genres.genres) {
            genreSqlHelper.addNewGenre(genre.id!!, genre.name!!)
        }

    }

    fun dispose() {
        if (!disposable!!.isDisposed())
            disposable!!.dispose()
    }

}