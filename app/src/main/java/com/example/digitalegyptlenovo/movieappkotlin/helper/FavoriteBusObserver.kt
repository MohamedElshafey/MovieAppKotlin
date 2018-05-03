package com.example.digitalegyptlenovo.movieappkotlin.helper

import com.example.digitalegyptlenovo.movieappkotlin.interfaces.FavoriteBus

/**
 * Created by Mohamed Elshafey on 5/3/2018.
 */
class FavoriteBusObserver {

    companion object {
        var subscribers = HashMap<String, FavoriteBus>()

        fun subscribe(key: String, listener: FavoriteBus) {
            subscribers[key] = listener
        }

        fun observe(key: String, isFavorite: Boolean) {
            subscribers[key]!!.isFavorite(isFavorite)
        }

    }
}