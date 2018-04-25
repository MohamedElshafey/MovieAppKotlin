package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable

/**
 * Created by Mohamed Elshafey on 4/24/2018.
 */
class ToolbarViewModel(var context: Context) : BaseObservable() {
    @Bindable
    var bool = true

}