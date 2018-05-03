package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.app.Activity
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.example.digitalegyptlenovo.movieappkotlin.BR
import com.example.digitalegyptlenovo.movieappkotlin.R
import com.example.digitalegyptlenovo.movieappkotlin.adapter.AllMovieAdapter
import com.example.digitalegyptlenovo.movieappkotlin.database.GenreSqlHelper
import com.example.digitalegyptlenovo.movieappkotlin.datamanager.GenreManager
import com.example.digitalegyptlenovo.movieappkotlin.datamanager.MovieManager
import com.example.digitalegyptlenovo.movieappkotlin.helper.NetworkHelper
import com.example.digitalegyptlenovo.movieappkotlin.interfaces.FetchDatabase
import com.example.digitalegyptlenovo.movieappkotlin.interfaces.LoadMore
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.room.Database.MovieDatabase
import com.example.digitalegyptlenovo.movieappkotlin.room.DbWorkerThread
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import java.io.Serializable

/**
 * Created by Mohamed Elshafey on 4/17/2018.
 */
class HomeViewModel(private val activity: Activity, retrofit: Retrofit) : BaseObservable(), Serializable {

    val loadMoreInterface = object : LoadMore {
        override fun load(page: Int) {
            if (canLoadMoreMovies && NetworkHelper.isNetworkAvailable(activity))
                showOnlineMovies(page)
        }
    }

    var canLoadMoreMovies: Boolean = true

    private var compositeDisposable = CompositeDisposable()

    @Bindable
    var progressEnable: Int = View.VISIBLE

    @Bindable
    var movies: ArrayList<Movie> = ArrayList()

    var movieAdapter: AllMovieAdapter = AllMovieAdapter(activity)

    private var movieDatabase: MovieDatabase? = null

    private var mDbWorkerThread: DbWorkerThread = DbWorkerThread("dbWorkerThread")

    private val genreSqlHelper = GenreSqlHelper(activity)

    private var moviesManager = MovieManager(retrofit)

    private var genreManager = GenreManager(retrofit, genreSqlHelper)

    @Bindable
    var showPlaceHolder = View.GONE;

    @Bindable
    var placeHolderResource: Int = R.drawable.offline_placeholder

    init {
        mDbWorkerThread.start()

        movieDatabase = MovieDatabase.getInstance(activity)

    }

    private fun enableProgressBar(show: Boolean) {
        progressEnable = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
        super.notifyPropertyChanged(BR.progressEnable)
    }

    private fun moviesLoaded(mMovies: List<Movie>) {
        movies.addAll(mMovies)
        super.notifyPropertyChanged(BR.movies)
    }

    fun selectMoviesOfCategory(page: Int) {
        enableProgressBar(true)
        movies.clear()
        canLoadMoreMovies = true
        showPlaceHolder = View.GONE;

        if (NetworkHelper.isNetworkAvailable(activity)) {
            if (genreSqlHelper.isTableEmpty()) {
                val observable = genreManager.get()
                observable.doOnComplete {
                    showOnlineMovies(page)
                }
            } else {
                showOnlineMovies(page)
            }
        } else
            showOfflineMovies()

    }

    private fun showOfflineMovies() {
        moviesManager.offline(movieDatabase!!, mDbWorkerThread, object : FetchDatabase {
            override fun found(movies: List<Movie>) {
                moviesLoaded(movies)

                enablePlaceHolder(false, 0)

                enableProgressBar(false)
            }

            override fun empty() {
                movies.clear()

                moviesLoaded(movies)

                enablePlaceHolder(true, R.drawable.empty_placeholder)

                enableProgressBar(false)
            }
        })
    }

    fun enablePlaceHolder(show: Boolean, imageResource: Int) {
        placeHolderResource = imageResource
        super.notifyPropertyChanged(BR.placeHolderResource)

        showPlaceHolder = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
        super.notifyPropertyChanged(BR.showPlaceHolder)
    }

    private fun showOnlineMovies(page: Int) {
        val popularObservable = moviesManager.online(page)
        compositeDisposable.add(popularObservable.subscribe({
            moviesLoaded(it.results)
            moviesManager.insertMovieDataInDb(movieDatabase!!, mDbWorkerThread, it.results)
            enableProgressBar(false)
            enablePlaceHolder(false, 0)
        }))
    }

    fun selectFavorite() {
        movies.clear()

        val list = movieDatabase!!.movieDAO().getFavoriteMovies()

        if (list.isEmpty())
            enablePlaceHolder(true, R.drawable.favorite_placeholder)
        else
            enablePlaceHolder(false, 0)

        moviesLoaded(list)
        canLoadMoreMovies = false
        enableProgressBar(false)
    }

    fun destroy() {
        compositeDisposable.dispose()
        genreManager.dispose()
        movieDatabase!!.destroyInstance()
        mDbWorkerThread.quit()
    }

}