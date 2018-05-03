package com.example.digitalegyptlenovo.movieappkotlin.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.digitalegyptlenovo.movieappkotlin.database.FeedGenreHelper.FeedEntry.COLUMN_ID
import com.example.digitalegyptlenovo.movieappkotlin.database.FeedGenreHelper.FeedEntry.COLUMN_NAME
import com.example.digitalegyptlenovo.movieappkotlin.database.FeedGenreHelper.FeedEntry.TABLE_NAME

/**
 * Created by Mohamed Elshafey on 4/17/2018.
 */
open class GenreSqlHelper(context: Context) {

    var db: SQLiteDatabase? = null

    fun addNewGenre(id: Int, name: String) {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_NAME, name);

        db!!.insert(TABLE_NAME, null, contentValues);
    }

    fun getGenreNamesByIds(selectionGenreIds: Array<String>): ArrayList<String> {
        val selection = getSelectionString(selectionGenreIds.size)
        val sortOrder = "$COLUMN_NAME DESC"

        val cursor = db!!.query(TABLE_NAME, null, selection, selectionGenreIds, null, null, sortOrder)

        val matchedGenresNames = ArrayList<String>()
        while (cursor.moveToNext()) {
            val genreName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
//            if (!matchedGenresNames.contains(genreName))
            matchedGenresNames.add(genreName)
        }
        cursor.close()

        return matchedGenresNames
    }

    private fun getSelectionString(length: Int): String {
        var selection = "";
        for (i in 0..length) {
            selection = selection.plus("$COLUMN_ID = ? ")
            if (i != length)
                selection = selection.plus(" OR ")
        }
        return selection
    }

    init {
        val mDbHelper = FeedGenreHelper(context)
        db = mDbHelper.writableDatabase
    }

    fun isTableEmpty(): Boolean {
        val count = "SELECT count(*) FROM $TABLE_NAME"
        val mCursor = db!!.rawQuery(count, null)
        mCursor.moveToFirst()
        val iCount = mCursor.getInt(0)
        mCursor.close()
        return iCount <= 0
    }
}