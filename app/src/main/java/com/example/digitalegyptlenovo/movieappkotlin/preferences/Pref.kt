package com.example.digitalegyptlenovo.movieappkotlin.preferences

import android.content.Context

/**
 * Created by Mohamed Elshafey on 4/24/2018.
 */
class Pref {


    companion object {
        fun init(context: Context) {
            pref = PreferenceHolder(context)
        }

        var pref: PreferenceHolder? = null

        @JvmStatic
        fun getInstance(): PreferenceHolder {
            return pref!!
        }
    }
}