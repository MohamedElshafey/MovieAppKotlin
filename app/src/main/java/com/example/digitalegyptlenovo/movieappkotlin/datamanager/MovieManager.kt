package com.example.digitalegyptlenovo.movieappkotlin.datamanager

import android.os.Handler
import com.example.digitalegyptlenovo.movieappkotlin.interfaces.FetchDatabase
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.model.Movies
import com.example.digitalegyptlenovo.movieappkotlin.room.Database.MovieDatabase
import com.example.digitalegyptlenovo.movieappkotlin.room.DbWorkerThread
import com.example.digitalegyptlenovo.movieappkotlin.webservice.MovieDbAPiConst
import com.example.digitalegyptlenovo.movieappkotlin.webservice.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

/**
 * Created by Mohamed Elshafey on 4/29/2018.
 */
class MovieManager(private var retrofit: Retrofit) {
    private var mUiHandler = Handler()

    fun online(page: Int): Observable<Movies> {
        val retrofitService = retrofit.create(RetrofitService::class.java)

        return getObservable(retrofitService, CategoryManager.getCategory(), page)
//        return retrofitService.listPopular(MovieDbAPiConst.apiKey, page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObservable(retrofitService: RetrofitService, category: CategoryManager.Category, page: Int): Observable<Movies> {
        return when (category) {
            CategoryManager.Category.NOW_PLAYING -> retrofitService
                    .listNowPlaying(MovieDbAPiConst.apiKey, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

            CategoryManager.Category.TOP_RATED -> retrofitService
                    .listTopRated(MovieDbAPiConst.apiKey, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

            else -> retrofitService
                    .listPopular(MovieDbAPiConst.apiKey, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun offline(appDataBase: MovieDatabase, mDbWorkerThread: DbWorkerThread, listener: FetchDatabase) {
        val task = Runnable {
            val movies = appDataBase.movieDAO().getAll(CategoryManager.getCategory().toString())

            mUiHandler.post({
                if (movies.isEmpty())
                    listener.empty()
                else
                    listener.found(movies)
            })
        }
        mDbWorkerThread.postTask(task)
    }

    fun insertMovieDataInDb(movieDataBase: MovieDatabase, mDbWorkerThread: DbWorkerThread, movie: ArrayList<Movie>) {
        val task = Runnable {
            movie.forEach {
                it.category = CategoryManager.getCategory().toString()
                movieDataBase.movieDAO().insert(it)
            }
        }
        mDbWorkerThread.postTask(task)
    }


}