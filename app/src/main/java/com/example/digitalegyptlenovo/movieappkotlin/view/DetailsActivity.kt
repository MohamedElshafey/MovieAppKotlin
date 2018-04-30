package com.example.digitalegyptlenovo.movieappkotlin.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.digitalegyptlenovo.movieappkotlin.App
import com.example.digitalegyptlenovo.movieappkotlin.R
import com.example.digitalegyptlenovo.movieappkotlin.databinding.ActivityDetailsBinding
import com.example.digitalegyptlenovo.movieappkotlin.model.Movie
import com.example.digitalegyptlenovo.movieappkotlin.viewmodel.DetailsViewModel
import retrofit2.Retrofit
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {
    @Inject
    lateinit var retrofit: Retrofit

    private var detailsViewModel: DetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).networkComponent?.inject(this)

        val binding: ActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val movie = intent.extras.get("movie") as Movie

        detailsViewModel = DetailsViewModel(this, retrofit, movie)

        binding.detailsVM = detailsViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsViewModel!!.dispose()
    }
}
