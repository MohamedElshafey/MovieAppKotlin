package com.example.digitalegyptlenovo.movieappkotlin.dagger.component

import com.example.digitalegyptlenovo.movieappkotlin.dagger.module.NetworkModule
import com.example.digitalegyptlenovo.movieappkotlin.view.DetailsActivity
import com.example.digitalegyptlenovo.movieappkotlin.view.HomeActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Mohamed Elshafey on 4/16/2018.
 */

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun inject(activity: HomeActivity)

    fun inject(activity: DetailsActivity)
}