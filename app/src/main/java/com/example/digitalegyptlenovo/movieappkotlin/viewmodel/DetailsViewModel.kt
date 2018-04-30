package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.app.Activity
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.digitalegyptlenovo.movieappkotlin.BR
import com.example.digitalegyptlenovo.movieappkotlin.datamanager.VideoManager
import com.example.digitalegyptlenovo.movieappkotlin.helper.YoutubeHelper
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.model.Videos
import com.example.digitalegyptlenovo.movieappkotlin.room.Database.MovieDatabase
import com.example.digitalegyptlenovo.movieappkotlin.room.DbWorkerThread
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit


/**
 * Created by Mohamed Elshafey on 4/29/2018.
 */
class DetailsViewModel(var activity: Activity, retrofit: Retrofit, var movie: Movie) : BaseObservable() {

    private var videoManager = VideoManager(retrofit)
    private var videos: Videos? = null
    private var compositeDisposable = CompositeDisposable()
    private val appDataBase = MovieDatabase.getInstance(activity)
    private var mDbWorkerThread: DbWorkerThread = DbWorkerThread("detailViewWorkerThread")

    @Bindable
    var favorite = movie.favorite

    init {
        mDbWorkerThread.start()

        val videoObservable = videoManager.get(movie.id)

        compositeDisposable.add(videoObservable.subscribe {
            videos = it
        })

        checkFavoriteInDb()
    }

    private fun checkFavoriteInDb() {
        favorite = appDataBase!!.movieDAO().isFavorite(movie.id)
        super.notifyPropertyChanged(BR.favorite)
    }

    fun checkChanged() {
        movie.favorite = movie.favorite.not()

        favorite = movie.favorite

        super.notifyPropertyChanged(BR.favorite)

        val task = Runnable {
            appDataBase!!.movieDAO().update(movie)
        }

        mDbWorkerThread.postTask(task)
    }

    fun openVideo() {
        YoutubeHelper.watchYoutubeVideo(activity, videos!!.results[0].key)
    }

    fun dispose() {
        compositeDisposable.dispose()
    }
}