//package com.example.digitalegyptlenovo.movieappkotlin.preferences
//
//import android.content.Context
//
///**
// * Created by Mohamed Elshafey on 4/24/2018.
// */
//class PreferenceHolder(context: Context) {
//
//    private var sharedPreference = context.getSharedPreferences("MAIN_PREF", Context.MODE_PRIVATE)
//
//    fun put(key: String, list: List<String>) {
//        val listAsString = list
//                .filter { s -> s != "" }
//                .distinct()
//                .joinToString(",")
//
//        sharedPreference.edit().putString(key, listAsString).apply()
//    }
//
//    fun get(key: String): List<String> {
//        return sharedPreference.getString(key, "").split(",")
//    }
//
//}