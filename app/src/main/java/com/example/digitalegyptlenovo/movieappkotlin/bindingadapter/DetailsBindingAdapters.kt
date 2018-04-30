package com.example.digitalegyptlenovo.movieappkotlin.bindingadapter

import android.app.Activity
import android.databinding.BindingAdapter
import android.support.v7.widget.Toolbar

/**
 * Created by Mohamed Elshafey on 4/30/2018.
 */
class DetailsBindingAdapters {

    companion object {

        @JvmStatic
        @BindingAdapter("finishActivity", requireAll = true)
        fun backAdapter(toolbar: Toolbar, activity: Activity) {
            toolbar.setNavigationOnClickListener { activity.finish() }
        }
    }
}