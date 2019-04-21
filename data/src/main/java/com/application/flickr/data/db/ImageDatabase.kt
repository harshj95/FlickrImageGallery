package com.application.flickr.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.application.flickr.data.db.DbConstants.CURRENT_DB_VERSION
import com.application.flickr.data.db.dao.ImageDao
import com.application.flickr.data.model.entity.SearchEntity
import com.application.flickr.data.model.entity.UrlEntity
import com.application.flickr.data.util.SingletonHolder

/**
 * Created by Harsh Jain on 18/04/19.
 */

@Database(entities = [SearchEntity::class, UrlEntity::class], version = CURRENT_DB_VERSION, exportSchema = false)
@TypeConverters(StringListTypeConverters::class)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    companion object : SingletonHolder<ImageDatabase, Application>({
        Room.databaseBuilder(it, ImageDatabase::class.java, DbConstants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    })
}