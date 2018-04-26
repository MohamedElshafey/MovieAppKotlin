package com.example.digitalegyptlenovo.movieappkotlin.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import android.widget.AdapterView
import com.example.digitalegyptlenovo.movieappkotlin.R

/**
 * Created by Mohamed Elshafey on 4/24/2018.
 */
class ToolbarViewModel(var context: Context) : BaseObservable() {
    @Bindable
    var textArrayResId = R.array.main_menu

    var itemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = parent!!.getItemAtPosition(position).toString()
            if (selectedItem == "Favorite") {

            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }
    }

}