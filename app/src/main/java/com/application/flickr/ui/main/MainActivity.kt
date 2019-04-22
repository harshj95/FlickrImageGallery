package com.application.flickr.ui.main

import android.content.Context
import android.content.Intent
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

        val imagesFragment: ImagesFragment

        if (fragment == null) {
            imagesFragment = ImagesFragment.newInstance(intent.getStringExtra("SEARCH_TERM"))
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, imagesFragment, "IMAGE_FRAGMENT")
                .commit()
        }

        registerNetworkChanges()
    }

    private fun registerNetworkChanges() {
        ConnectionLiveData(this).observe(this, Observer {
            mainViewModel.isConnected = it?.isConnected == true
        })
    }

    companion object {
        fun notificationIntent(context: Context, searchTerm: String): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("SEARCH_TERM", searchTerm)
            return intent
        }
    }
}