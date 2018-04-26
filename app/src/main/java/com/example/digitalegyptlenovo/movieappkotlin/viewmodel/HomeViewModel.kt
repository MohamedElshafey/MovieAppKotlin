package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.app.Activity
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.example.digitalegyptlenovo.movieappkotlin.BR
import com.example.digitalegyptlenovo.movieappkotlin.adapter.AllMovieAdapter
import com.example.digitalegyptlenovo.movieappkotlin.database.GenreSqlHelper
import com.example.digitalegyptlenovo.movieappkotlin.helper.NetworkHelper
import com.example.digitalegyptlenovo.movieappkotlin.interfaces.LoadMore
import com.example.digitalegyptlenovo.movieappkotlin.model.Genres
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.room.Database.MovieDatabase
import com.example.digitalegyptlenovo.movieappkotlin.room.DbWorkerThread
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
            if (NetworkHelper.isNetworkAvailable(activity))
                getPopularMovies(page)
        }
    }

    private var compositeDisposable = CompositeDisposable()

    @Bindable
    var progressEnable: Int = View.VISIBLE

    @Bindable
    var movies: ArrayList<Movie> = ArrayList()

    var movieAdapter: AllMovieAdapter = AllMovieAdapter(activity)

    private var appDataBase: MovieDatabase? = null

    private var mDbWorkerThread: DbWorkerThread

    private val mUiHandler = Handler()

    init {
        val genreSqlHelper = GenreSqlHelper(activity)
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")

        mDbWorkerThread.start()

        appDataBase = MovieDatabase.getInstance(activity)

        if (NetworkHelper.isNetworkAvailable(activity)) {

            if (genreSqlHelper.isTableEmpty())
                getGenresThenGetPopularMovies(1)
            else
                getPopularMovies(1)

        } else {

            val task = Runnable {
                val movies = appDataBase!!.movieDAO().getAll()

                mUiHandler.post({
                    if (movies.isEmpty())
                        Toast.makeText(activity, "No data in cache ... ", Toast.LENGTH_SHORT).show()
                    else
                        moviesLoaded(movies)
                })
            }
            mDbWorkerThread.postTask(task)

            hideProgress()
        }

    }

    private fun insertMovieDataInDb(movie: ArrayList<Movie>) {
        val task = Runnable {
            movie.forEach { appDataBase!!.movieDAO().insert(it) }
        }
        mDbWorkerThread.postTask(task)
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

    private fun moviesLoaded(mMovies: List<Movie>) {
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
                    insertMovieDataInDb(it.results)
                    hideProgress()
                })

        compositeDisposable.add(popularDisposable)
    }

    private fun dispose() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    fun destroy() {
        dispose()
        appDataBase!!.destroyInstance()
        mDbWorkerThread.quit()
    }

}
