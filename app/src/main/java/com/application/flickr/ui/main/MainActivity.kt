package com.application.flickr.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.application.flickr.R
import com.application.flickr.data.util.ConnectionLiveData
import com.application.flickr.ui.common.BaseActivity
import com.application.flickr.util.extensions.isNetworkConnected

class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.isConnected = isNetworkConnected()

        val fragment = supportFragmentManager.findFragmentByTag("IMAGE_FRAGMENT")

        val fragmentOne: ImagesFragment

        if (fragment == null) {
            fragmentOne = ImagesFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragmentOne, "IMAGE_FRAGMENT")
                .commit()
        }

        registerNetworkChanges()
    }

    private fun registerNetworkChanges() {
        ConnectionLiveData(this).observe(this, Observer {
            mainViewModel.isConnected = it?.isConnected == true
        })
    }
}