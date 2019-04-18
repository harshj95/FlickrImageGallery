package com.application.flickr.config

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}
