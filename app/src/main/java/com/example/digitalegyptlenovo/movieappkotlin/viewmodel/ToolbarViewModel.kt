package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.digitalegyptlenovo.movieappkotlin.R
import java.io.Serializable

/**
 * Created by Mohamed Elshafey on 4/24/2018.
 */
class ToolbarViewModel(var context: Context) : BaseObservable(), Serializable {
    @Bindable
    var textArrayResId = R.array.main_menu

}