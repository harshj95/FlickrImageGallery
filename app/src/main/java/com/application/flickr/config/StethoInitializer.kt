package com.application.flickr.config

import android.app.Application
import com.facebook.stetho.Stetho

class StethoInitializer : AppInitializer {
    override fun init(application: Application) =
        Stetho.initializeWithDefaults(application)
}
