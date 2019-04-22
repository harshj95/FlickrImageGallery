package com.application.flickr.ui.main

import android.os.Bundle
import com.application.flickr.R
import com.application.flickr.ui.common.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentByTag("IMAGE_FRAGMENT")

        val fragmentOne: ImagesFragment

        if (fragment == null) {
            fragmentOne = ImagesFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragmentOne, "IMAGE_FRAGMENT")
                .commit()
        }
    }
}