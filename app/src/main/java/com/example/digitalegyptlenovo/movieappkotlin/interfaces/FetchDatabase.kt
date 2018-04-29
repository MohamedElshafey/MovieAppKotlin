package com.example.digitalegyptlenovo.movieappkotlin.interfaces

import com.example.digitalegyptlenovo.movieappkotlin.model.Movie

/**
 * Created by Mohamed Elshafey on 4/29/2018.
 */
interface FetchDatabase {

    fun found(movies: List<Movie>)

    fun empty()

}