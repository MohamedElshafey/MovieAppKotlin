package com.example.digitalegyptlenovo.movieappkotlin.room

import android.os.Handler
import android.os.HandlerThread

/**
 * Created by Mohamed Elshafey on 4/25/2018.
 */
class DbWorkerThread(threadName: String) : HandlerThread(threadName) {

    private var mWorkerHandler: Handler? = null

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        mWorkerHandler = Handler(looper)
    }

    fun postTask(task: Runnable) {
        mWorkerHandler!!.post(task)
    }

}