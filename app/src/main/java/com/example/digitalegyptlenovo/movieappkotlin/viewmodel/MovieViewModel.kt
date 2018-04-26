package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.digitalegyptlenovo.movieappkotlin.BR
import com.example.digitalegyptlenovo.movieappkotlin.database.GenreSqlHelper
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.room.Database.MovieDatabase
import com.example.digitalegyptlenovo.movieappkotlin.room.DbWorkerThread

/**
 * Created by Mohamed Elshafey on 4/22/2018.
 */
class MovieViewModel(private var context: Context, var movie: Movie) : BaseObservable() {
    var genresCollection = ""

    @Bindable
    var favorite = movie.favorite

    private var mDbWorkerThread: DbWorkerThread = DbWorkerThread("movieWorkerThread")

    private val appDataBase = MovieDatabase.getInstance(context)

    init {
        mDbWorkerThread.start()

        getGenres(movie.genre_ids)

        checkFavoriteInDb()
    }

    private fun checkFavoriteInDb() {
        favorite = appDataBase!!.movieDAO().getFavorite(movie.id)
        super.notifyPropertyChanged(BR.favorite)
    }

    private fun getGenres(genre_ids: Array<String>) {
        val genreNames = GenreSqlHelper(context).getGenreNamesByIds(genre_ids)
        for (genreName in genreNames) {
            genresCollection += genreName
            if (genreNames.indexOf(genreName) < genreNames.size - 1)
                genresCollection += " ,"
        }
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

}