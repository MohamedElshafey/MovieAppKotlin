package com.example.digitalegyptlenovo.movieappkotlin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.digitalegyptlenovo.movieappkotlin.R
import com.example.digitalegyptlenovo.movieappkotlin.databinding.MovieItemBinding
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.viewmodel.MovieViewModel


/**
 * Created by Mohamed Elshafey on 4/18/2018.
 */
class AllMovieAdapter(private val context: Context) : BaseAdapter() {

    private var movies: ArrayList<Movie> = ArrayList()

    fun updateData(movies: ArrayList<Movie>) {
        this.movies = movies;
        super.notifyDataSetChanged()
    }

    @SuppressLint("ViewHolder")
    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val binding: MovieItemBinding = DataBindingUtil.inflate(inflater, R.layout.movie_item, viewGroup, false)

        val movie: Movie = movies[i]

        binding.movieVM = MovieViewModel(context, movie)

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