package com.application.flickr.data.prefs

import android.app.Application
import android.content.Context
import com.application.flickr.data.util.SingletonHolder

/**
 * Created by Harsh Jain on 18/02/19.
 */

class DbPreferenceManager private constructor(context: Application) : SharedPreferenceManager(context) {

    private val dbPreferenceName = "db_pref"

    init {
        pref = context.getSharedPreferences(dbPreferenceName, Context.MODE_PRIVATE)
    }


    var isFirstRun: Boolean
        get() = getBoolean(KEY_FIRST_RUN, true)
        set(value) {
            putBoolean(KEY_FIRST_RUN, value)
        }

    companion object : SingletonHolder<DbPreferenceManager, Application>(::DbPreferenceManager) {
        const val KEY_FIRST_RUN = "first_run"
        const val CACHE_MAP = "cache_map"
    }
}