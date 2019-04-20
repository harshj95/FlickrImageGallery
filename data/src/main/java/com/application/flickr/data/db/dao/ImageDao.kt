package com.application.flickr.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.application.flickr.data.model.entity.SearchEntity

/**
 * Created by Harsh Jain on 19/04/19.
 */

@Dao
abstract class ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSearchEntity(searchEntity: SearchEntity)

    @Query("select * from searchentity where searchTerm = :searchTerm")
    abstract fun getSearchEntityBySearchTerm(searchTerm: String)

    @Query("select urls from searchentity where searchTerm = :searchTerm")
    abstract fun getUrlsBySearchTerm(searchTerm: String)

    @Query("delete from searchentity where searchTerm = :searchTerm")
    abstract fun deleteEntityBySearchTerm(searchTerm: String)
}