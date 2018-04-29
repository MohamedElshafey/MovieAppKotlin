package com.example.digitalegyptlenovo.movieappkotlin.room.DAO

import android.arch.persistence.room.*
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie


/**
 * Created by Mohamed Elshafey on 4/25/2018.
 */
@Dao
interface MovieDAO {

    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie where favorite = 1")
    fun getFavoriteMovies(): List<Movie>

    @Query("SELECT movie.favorite FROM movie where id LIKE :movieId")
    fun isFavorite(movieId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg movies: Movie)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movies: Movie)
}