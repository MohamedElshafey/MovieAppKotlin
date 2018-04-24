package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.database.GenreSqlHelper

/**
 * Created by Mohamed Elshafey on 4/22/2018.
 */
class MovieViewModel(var context: Context, var movie: Movie) : BaseObservable() {
    var genresCollection = ""

    init {
        genresCollection = getGenres(movie.genre_ids)
    }

    private fun getGenres(genre_ids: Array<String>): String {
        val genreNames = GenreSqlHelper(context).getGenreNamesByIds(genre_ids)

        var collectedGenres = ""
        for (genreName in genreNames) {
            collectedGenres += genreName
            if (genreNames.indexOf(genreName) < genreNames.size - 1)
                collectedGenres += " ,"
        }

        return collectedGenres
    }


}