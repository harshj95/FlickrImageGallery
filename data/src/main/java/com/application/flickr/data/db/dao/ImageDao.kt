package com.application.flickr.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.application.flickr.data.model.entity.SearchEntity
import com.application.flickr.data.model.entity.UrlEntity

/**
 * Created by Harsh Jain on 19/04/19.
 */

@Dao
abstract class ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSearchEntity(searchEntity: SearchEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUrlEntity(urlEntity: UrlEntity)

    @Update
    abstract fun updateSearchEntity(searchEntity: SearchEntity)

    @Query("select * from searchentity order by timestamp")
    abstract fun getOrderedSearchEntities(): List<SearchEntity>

    @Query("select * from searchentity where searchTerm = :searchTerm")
    abstract fun getSearchEntityBySearchTerm(searchTerm: String): SearchEntity

    @Query("select * from urlentity where searchTerm = :searchTerm")
    abstract fun getUrlsBySearchTerm(searchTerm: String): List<UrlEntity>

    @Query("select * from urlentity where searchTerm = :searchTerm")
    abstract fun getUrlsBySearchTermLiveData(searchTerm: String): LiveData<List<UrlEntity>>

    @Query("delete from searchentity where searchTerm = :searchTerm")
    abstract fun deleteEntityBySearchTerm(searchTerm: String)

    @Query("delete from urlentity where searchTerm = :searchTerm")
    abstract fun deleteUrlsBySearchTerm(searchTerm: String)
}