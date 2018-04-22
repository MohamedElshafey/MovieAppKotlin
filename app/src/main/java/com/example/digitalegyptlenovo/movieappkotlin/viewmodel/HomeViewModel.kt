package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.app.Activity
import android.databinding.BaseObservable
import android.util.Log
import android.view.View
import android.widget.AbsListView
import com.example.digitalegyptlenovo.movieappkotlin.adapter.AllMovieAdapter
import com.example.digitalegyptlenovo.movieappkotlin.databinding.ActivityHomeBinding
import com.example.digitalegyptlenovo.movieappkotlin.model.Genres
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.sqlite.helper.GenreSqlHelper
import com.example.digitalegyptlenovo.movieappkotlin.webservice.MovieDbAPiConst
import com.example.digitalegyptlenovo.movieappkotlin.webservice.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

/**
 * Created by Mohamed Elshafey on 4/17/2018.
 */
class HomeViewModel(val activity: Activity, val binding: ActivityHomeBinding, val retrofit: Retrofit) : BaseObservable() {
    var page = 1;
    private var movies: ArrayList<Movie>? = ArrayList();

    init {

        val genreSqlHelper = GenreSqlHelper(activity);

        if (genreSqlHelper.isTableEmpty())
            getGenresThenGetPopularMovies()
        else
            getPopularMovies()

        binding.gridview.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                try {
                    if (!(binding.gridview.lastVisiblePosition != binding.gridview.adapter!!.count - 1
                                    || binding.gridview.getChildAt(binding.gridview.childCount - 1).bottom > binding.gridview.height)) {
                        page++
                        Log.w("LOADING_PAGES:", " start load: $page")
//                        binding.log.text = String.format("loading page %s", page);
                        getPopularMovies()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, i: Int) {
            }
        })

    }


    private var disposable: Disposable? = null

    private fun getGenresThenGetPopularMovies() {
        Log.d("HOMEVIEW_MODEL", "getGenresThenGetPopularMovies: ")

        val retrofitService = retrofit.create(RetrofitService::class.java)

        val observable: Observable<Genres> = retrofitService.listGenres(MovieDbAPiConst.apiKey)

        disposable = observable.map {
            saveGenres(it)
            return@map retrofitService.listPopularMovies(MovieDbAPiConst.apiKey, page)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.doOnNext({
                        movies!!.addAll(it.results)
                        setPopularMoviesAdapter()
                        binding.progressBar.visibility = View.GONE
                    })
                }
                )
    }

    private fun setPopularMoviesAdapter() {
        if (binding.gridview.adapter == null) {
            val movieAdapter = AllMovieAdapter(activity, movies!!)
            binding.gridview.adapter = movieAdapter
        } else {
            (binding.gridview.adapter as AllMovieAdapter).movies = movies!!;
            (binding.gridview.adapter as AllMovieAdapter).notifyDataSetChanged()
        }
    }

    private fun saveGenres(genres: Genres) {
        val genreSqlHelper = GenreSqlHelper(activity)
        for (genre in genres.genres) {
            genreSqlHelper.addNewGenre(genre.id, genre.name)
        }

    }

    private fun getPopularMovies() {
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val observable = retrofitService.listPopularMovies(MovieDbAPiConst.apiKey, page)
        disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movies!!.addAll(it.results)
                    setPopularMoviesAdapter()
                    binding.progressBar.visibility = View.GONE
                    Log.w("LOADING_PAGES:", " finish load: $page")
//                    binding.log.text = String.format("page %s is loaded successfully ..", page);
                })
    }

    fun dispose() {
        if (!disposable!!.isDisposed())
            disposable!!.dispose()
    }

}
