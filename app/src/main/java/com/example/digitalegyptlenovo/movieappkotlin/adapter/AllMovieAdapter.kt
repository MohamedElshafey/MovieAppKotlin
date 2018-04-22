package com.example.digitalegyptlenovo.movieappkotlin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.digitalegyptlenovo.movieappkotlin.R
import com.example.digitalegyptlenovo.movieappkotlin.databinding.MovieItemBinding
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.sqlite.helper.GenreSqlHelper

/**
 * Created by Mohamed Elshafey on 4/18/2018.
 */
class AllMovieAdapter(val context: Context, var movies: ArrayList<Movie>) : BaseAdapter() {



    @SuppressLint("ViewHolder")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val binding: MovieItemBinding = DataBindingUtil.inflate(inflater, R.layout.movie_item, viewGroup, false)

        val movie: Movie = movies.get(i)

        val mainImageUrl = "https://image.tmdb.org/t/p/w500"
        val posterPath = movie.poster_path
        val fullImageUrl = mainImageUrl.plus(posterPath)

        Glide.with(context).load(fullImageUrl).into(binding.image)

        binding.movieName.text = movie.title

        val genreSqlHelper = GenreSqlHelper(context)

        val genreNames = genreSqlHelper.getGenreNamesByIds(movie.genre_ids)

        var genres = ""
        for (genreName in genreNames) {
            genres = genres.plus(genreName)
            if (genreNames.indexOf(genreName) < genreNames.size - 1)
                genres = genres.plus(" ,")
        }

        binding.info.text = genres

        return binding.root
    }

    override fun getItem(i: Int): Any {
        return 0
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return movies.size
    }


}