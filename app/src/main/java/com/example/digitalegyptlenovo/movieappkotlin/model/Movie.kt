package com.example.digitalegyptlenovo.movieappkotlin.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

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
            @ColumnInfo(name = "favorite") var favorite: Boolean,
            @ColumnInfo(name = "category") var category: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readFloat(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.createStringArray(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString()) {
    }

    fun getPosterPath(): String {
        return "https://image.tmdb.org/t/p/w500$poster_path"
    }

    fun getBackDropPath(): String {
        return "https://image.tmdb.org/t/p/w500$backdrop_path"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(vote_count)
        parcel.writeInt(id)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeFloat(vote_average)
        parcel.writeString(title)
        parcel.writeDouble(popularity)
        parcel.writeString(poster_path)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(backdrop_path)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeStringArray(genre_ids)
        parcel.writeString(overview)
        parcel.writeString(release_date)
        parcel.writeByte(if (favorite) 1 else 0)
        parcel.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}