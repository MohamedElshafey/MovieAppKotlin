package com.example.digitalegyptlenovo.movieappkotlin.bindingadapter

import android.databinding.BindingAdapter
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Spinner
import com.example.digitalegyptlenovo.movieappkotlin.R
import com.example.digitalegyptlenovo.movieappkotlin.adapter.AllMovieAdapter
import com.example.digitalegyptlenovo.movieappkotlin.interfaces.LoadMore
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie

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
        @BindingAdapter("dataResource", requireAll = true)
        fun spinnerAdapter(spinner: Spinner, bool: Boolean) {
            val adapter = ArrayAdapter.createFromResource(spinner.context, R.array.main_menu, R.layout.spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
}