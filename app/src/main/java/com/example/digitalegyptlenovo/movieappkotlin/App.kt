package com.example.digitalegyptlenovo.movieappkotlin

import android.app.Application
import com.example.digitalegyptlenovo.movieappkotlin.dagger.component.DaggerNetworkComponent
import com.example.digitalegyptlenovo.movieappkotlin.dagger.component.NetworkComponent
import com.example.digitalegyptlenovo.movieappkotlin.dagger.module.NetworkModule
import com.example.digitalegyptlenovo.movieappkotlin.preferences.Pref
import com.example.digitalegyptlenovo.movieappkotlin.webservice.MovieDbAPiConst

/**
 * Created by Mohamed Elshafey on 4/16/2018.
 */
class App : Application() {
    var networkComponent: NetworkComponent? = null

    override fun onCreate() {
        super.onCreate()

        Pref.init(applicationContext)

        networkComponent = DaggerNetworkComponent.builder()
                .networkModule(NetworkModule(MovieDbAPiConst.baseUrl))
                .build()
    }
}