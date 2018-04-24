package com.example.digitalegyptlenovo.movieappkotlin.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import com.example.digitalegyptlenovo.movieappkotlin.database.DatabaseConstants.Companion.DATABASE_NAME
import com.example.digitalegyptlenovo.movieappkotlin.database.DatabaseConstants.Companion.DATABASE_VERSION

/**
 * Created by Mohamed Elshafey on 4/17/2018.
 */
class FeedGenreHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,
        null, DATABASE_VERSION) {

    private val sqlCreateEntries = ("CREATE TABLE $TABLE_NAME($_ID INTEGER PRIMARY KEY,$COLUMN_ID TEXT,$COLUMN_NAME TEXT)")

    private val sqlDeleteEntries = "DROP TABLE IF EXISTS $TABLE_NAME"


    companion object FeedEntry : BaseColumns {
        const val TABLE_NAME = "genre"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase?.execSQL(sqlDeleteEntries)
        onCreate(sqLiteDatabase)
    }


    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.execSQL(sqlCreateEntries)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}