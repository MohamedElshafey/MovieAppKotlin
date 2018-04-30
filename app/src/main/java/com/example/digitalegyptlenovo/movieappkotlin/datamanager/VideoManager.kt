package com.example.digitalegyptlenovo.movieappkotlin.datamanager

import com.example.digitalegyptlenovo.movieappkotlin.model.Videos
import com.example.digitalegyptlenovo.movieappkotlin.webservice.MovieDbAPiConst
import com.example.digitalegyptlenovo.movieappkotlin.webservice.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

/**
 * Created by Mohamed Elshafey on 4/30/2018.
 */
class VideoManager(private var retrofit: Retrofit) {

    fun get(videoId: Int): Observable<Videos> {
        val retrofitService = retrofit.create(RetrofitService::class.java)

        return retrofitService.movieVideos(videoId, MovieDbAPiConst.apiKey).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}