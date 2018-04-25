package com.example.digitalegyptlenovo.movieappkotlin.model

/**
 * Created by Mohamed Elshafey on 4/18/2018.
 */

class Movie constructor(var vote_count: Int, var id: Int, var video: Boolean, var vote_average: Float,
                        var title: String, var popularity: Double, var poster_path: String,
                        var original_language: String, var original_title: String,
                        var genre_ids: Array<String>, var backdrop_path: String, var adult: Boolean,
                        var overview: String, var release_date: String) {

    fun getPosterPath(): String {
        return "https://image.tmdb.org/t/p/w500$poster_path"
    }

}