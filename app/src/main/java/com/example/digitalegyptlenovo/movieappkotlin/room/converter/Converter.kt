package com.example.digitalegyptlenovo.movieappkotlin.room.converter

import android.arch.persistence.room.TypeConverter

/**
 * Created by Mohamed Elshafey on 4/26/2018.
 */
class Converter {

    @TypeConverter
    fun toString(array: Array<String>): String {
        return array.joinToString(",")
    }

    @TypeConverter
    fun toArray(string: String): Array<String> {
        return string.split(",").toTypedArray()
    }
}