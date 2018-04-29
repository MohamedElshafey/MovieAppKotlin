package com.example.digitalegyptlenovo.movieappkotlin.bindingadapter

import android.databinding.BindingAdapter
import android.view.View
import android.widget.*
import com.example.digitalegyptlenovo.movieappkotlin.R
import com.example.digitalegyptlenovo.movieappkotlin.adapter.AllMovieAdapter
import com.example.digitalegyptlenovo.movieappkotlin.datamanager.CategoryManager
import com.example.digitalegyptlenovo.movieappkotlin.datamanager.CategoryManager.Category.*
import com.example.digitalegyptlenovo.movieappkotlin.interfaces.LoadMore
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.viewmodel.HomeViewModel

/**
 * Created by Mohamed Elshafey on 4/22/2018.
 */
class HomeViewBindingAdapters {

    companion object {
        @JvmStatic
        @BindingAdapter("adapter", "data", requireAll = true)
        fun addGridAdapter(gridView: GridView, adapter: AllMovieAdapter, data: ArrayList<Movie>) {
            if (gridView.adapter == null)
                gridView.adapter = adapter
            adapter.updateData(data)
        }

        @JvmStatic
        @BindingAdapter("loadMore", requireAll = true)
        fun endlessScroll(gridView: GridView, loadMoreInterface: LoadMore) {
            var page = 1
            gridView.setOnScrollListener(object : AbsListView.OnScrollListener {
                override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                    try {
                        if (!(gridView.lastVisiblePosition != gridView.adapter!!.count - 1
                                        || gridView.getChildAt(gridView.childCount - 1).bottom > gridView.height)) {
                            page++
                            loadMoreInterface.load(page)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onScrollStateChanged(view: AbsListView?, i: Int) {
                }
            })
        }

        @JvmStatic
        @BindingAdapter("textArrayResId", "homeViewModel", requireAll = true)
        fun spinnerAdapter(spinner: Spinner, textArrayResId: Int,
                           homeViewModel: HomeViewModel) {
            val adapter = ArrayAdapter.createFromResource(spinner.context, textArrayResId, R.layout.spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedItem = parent!!.getItemAtPosition(position).toString()
                    if (selectedItem == "Favorite") {
                        CategoryManager.setCategory(FAVORITE)
                        homeViewModel.selectFavorite()
                    } else {
                        if (selectedItem == "Popular")
                            CategoryManager.setCategory(POPULAR)
                        else if (selectedItem == "Top Rated")
                            CategoryManager.setCategory(TOP_RATED)
                        else if (selectedItem == "Newest")
                            CategoryManager.setCategory(NOW_PLAYING)

                        homeViewModel.selectMoviesOfCategory(1)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
    }
}