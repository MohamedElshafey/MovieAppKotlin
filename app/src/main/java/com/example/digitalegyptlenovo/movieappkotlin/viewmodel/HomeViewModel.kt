package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.app.Activity
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.example.digitalegyptlenovo.movieappkotlin.BR
import com.example.digitalegyptlenovo.movieappkotlin.adapter.AllMovieAdapter
import com.example.digitalegyptlenovo.movieappkotlin.bindingadapter.LoadMore
import com.example.digitalegyptlenovo.movieappkotlin.database.GenreSqlHelper
import com.example.digitalegyptlenovo.movieappkotlin.model.Genres
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.webservice.MovieDbAPiConst
import com.example.digitalegyptlenovo.movieappkotlin.webservice.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

/**
 * Created by Mohamed Elshafey on 4/17/2018.
 */
class HomeViewModel(private val activity: Activity, private val retrofit: Retrofit) : BaseObservable() {

    val loadMoreInterface = object : LoadMore {
        override fun load(page: Int) {
            getPopularMovies(page)
        }
    }

    private var compositeDisposable = CompositeDisposable()

    @Bindable
    var progressEnable: Int = View.VISIBLE

    @Bindable
    var movies: ArrayList<Movie> = ArrayList()

    var movieAdapter: AllMovieAdapter = AllMovieAdapter(activity)

    init {
        val genreSqlHelper = GenreSqlHelper(activity)

        val page = 1
        if (genreSqlHelper.isTableEmpty())
            getGenresThenGetPopularMovies(page)
        else
            getPopularMovies(page)
    }

    private fun getGenresThenGetPopularMovies(page: Int) {
        val retrofitService = retrofit.create(RetrofitService::class.java)

        val observable: Observable<Genres> = retrofitService.listGenres(MovieDbAPiConst.apiKey)

        val genresDisposable = observable.map {
            saveGenresToDB(it)
            getPopularMovies(page)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        compositeDisposable.add(genresDisposable)
    }

    private fun hideProgress() {
        if (progressEnable == View.VISIBLE) {
            progressEnable = View.GONE
            super.notifyPropertyChanged(BR.progressEnable)
        }
    }

    private fun moviesLoaded(mMovies: ArrayList<Movie>) {
        movies.addAll(mMovies)

        super.notifyPropertyChanged(BR.movies)
    }

    private fun saveGenresToDB(genres: Genres) {
        val genreSqlHelper = GenreSqlHelper(activity)
        for (genre in genres.genres) {
            genreSqlHelper.addNewGenre(genre.id, genre.name)
        }

    }

    private fun getPopularMovies(page: Int) {
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val observable = retrofitService.listPopularMovies(MovieDbAPiConst.apiKey, page)
        val popularDisposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    moviesLoaded(it.results)

                    hideProgress()
                })

        compositeDisposable.add(popularDisposable)
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

}
