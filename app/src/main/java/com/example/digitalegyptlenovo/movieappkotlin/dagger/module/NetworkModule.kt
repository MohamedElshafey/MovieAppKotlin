package com.example.digitalegyptlenovo.movieappkotlin.dagger.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Mohamed Elshafey on 4/16/2018.
 */

@Module
class NetworkModule(private val baseUrl: String) {

//    private val mBaseUrl: String = ""

//    constructor(baseUrl: String) : this() {
//        this.mBaseUrl = baseUrl
//    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create();
    }

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

}