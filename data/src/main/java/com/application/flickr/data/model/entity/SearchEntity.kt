package com.application.flickr.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.application.flickr.data.db.StringListTypeConverters

/**
 * Created by Harsh Jain on 19/04/19.
 */

@Entity
data class SearchEntity(
    var searchTerm: String,
    @TypeConverters(StringListTypeConverters::class)
    var urls: List<String>
) {
    init {
        searchTerm = searchTerm.toLowerCase() //Assuming search terms are saved as lowercase only
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}