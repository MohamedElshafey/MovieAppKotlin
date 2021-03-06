package com.example.digitalegyptlenovo.movieappkotlin.view

import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.digitalegyptlenovo.movieappkotlin.App
import com.example.digitalegyptlenovo.movieappkotlin.R
import com.example.digitalegyptlenovo.movieappkotlin.databinding.ActivityHomeBinding
import com.example.digitalegyptlenovo.movieappkotlin.viewmodel.HomeViewModel
import com.example.digitalegyptlenovo.movieappkotlin.viewmodel.ToolbarViewModel
import retrofit2.Retrofit
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit
    private var homeVieModel: HomeViewModel? = null
    private var toolbarViewModel: ToolbarViewModel? = null
    private var binding: ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).networkComponent?.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        homeVieModel = HomeViewModel(this, retrofit)
        binding!!.homeViewModel = homeVieModel

        toolbarViewModel = ToolbarViewModel(this)
        binding!!.toolbarVM = toolbarViewModel

    }

    override fun onDestroy() {
        homeVieModel!!.destroy()
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        binding!!.gridview.numColumns = resources.getInteger(R.integer.grid_columns)
    }
}
