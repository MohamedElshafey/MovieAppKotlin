package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.digitalegyptlenovo.movieappkotlin.BR
import com.example.digitalegyptlenovo.movieappkotlin.database.GenreSqlHelper
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.preferences.Pref

/**
 * Created by Mohamed Elshafey on 4/22/2018.
 */
class MovieViewModel(private var context: Context, var movie: Movie) : BaseObservable() {
    var genresCollection = ""

    @Bindable
    var favorite = false

    private var favMoviesPrefTag = "FavoriteMovies"

    init {
        getGenres(movie.genre_ids)

        checkFavorite()
    }

    private fun checkFavorite() {
        val favorites: List<String> = Pref.getInstance().get(favMoviesPrefTag)
        favorite = movie.id.toString() in favorites
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
        if (favorite)
            removeFromFavorites()
        else
            addToFavorites()

        favorite = favorite.not()
        super.notifyPropertyChanged(BR.favorite)
    }

    private fun addToFavorites() {
        var favorites: List<String> = Pref.getInstance().get(favMoviesPrefTag)
        favorites = favorites.plusElement(movie.id.toString())
        Pref.getInstance().put(favMoviesPrefTag, favorites)
    }

    private fun removeFromFavorites() {
        var favorites = Pref.getInstance().get(favMoviesPrefTag)
        favorites = favorites.minus(movie.id.toString())
        Pref.getInstance().put(favMoviesPrefTag, favorites)
    }

}