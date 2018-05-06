package com.example.digitalegyptlenovo.movieappkotlin

import android.app.Application
import com.example.digitalegyptlenovo.movieappkotlin.dagger.component.DaggerNetworkComponent
import com.example.digitalegyptlenovo.movieappkotlin.dagger.component.NetworkComponent
import com.example.digitalegyptlenovo.movieappkotlin.dagger.module.NetworkModule
import com.example.digitalegyptlenovo.movieappkotlin.webservice.MovieDbAPiConst
import com.squareup.leakcanary.LeakCanary

/**
 * Created by Mohamed Elshafey on 4/16/2018.
 */
class App : Application() {
    var networkComponent: NetworkComponent? = null

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        networkComponent = DaggerNetworkComponent.builder()
                .networkModule(NetworkModule(MovieDbAPiConst.baseUrl))
                .build()
    }
}