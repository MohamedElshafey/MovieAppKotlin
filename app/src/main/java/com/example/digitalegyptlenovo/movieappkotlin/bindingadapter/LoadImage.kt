package com.example.digitalegyptlenovo.movieappkotlin.bindingadapter

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by Mohamed Elshafey on 4/22/2018.
 */
class LoadImage {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl", requireAll = true)
        fun load(imageView: ImageView, imagePath: String) {
            Glide.with(imageView).load(imagePath).into(imageView)
        }
    }
}