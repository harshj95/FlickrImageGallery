package com.application.flickr.config.log

import android.app.Application
import com.application.flickr.config.AppInitializer
import com.application.flickr.util.C

/**
 * Created by Harsh Jain on 18/04/19.
 */

class TimberInitializer constructor(private val timberLogger: Logger) : AppInitializer {
    override fun init(application: Application) {
        if (timberLogger is TimberLogger) {
            timberLogger.setup(C.debug)
        }
    }
}
