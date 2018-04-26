package com.example.digitalegyptlenovo.movieappkotlin.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Mohamed Elshafey on 4/18/2018.
 */
@Entity(tableName = "movie")
class Movie(@ColumnInfo(name = "vote_count") var vote_count: Int,
            @PrimaryKey var id: Int,
            @ColumnInfo(name = "video") var video: Boolean,
            @ColumnInfo(name = "vote_average") var vote_average: Float,
            @ColumnInfo(name = "title") var title: String,
            @ColumnInfo(name = "popularity") var popularity: Double,
            @ColumnInfo(name = "poster_path") var poster_path: String,
            @ColumnInfo(name = "original_language") var original_language: String,
            @ColumnInfo(name = "original_title") var original_title: String,
            @ColumnInfo(name = "backdrop_path") var backdrop_path: String,
            @ColumnInfo(name = "adult") var adult: Boolean,
            @ColumnInfo(name = "genre_ids") var genre_ids: Array<String>,
            @ColumnInfo(name = "overview") var overview: String,
            @ColumnInfo(name = "release_date") var release_date: String,
            @ColumnInfo(name = "favorite") var favorite: Boolean) {

    fun getPosterPath(): String {
        return "https://image.tmdb.org/t/p/w500$poster_path"
    }

}