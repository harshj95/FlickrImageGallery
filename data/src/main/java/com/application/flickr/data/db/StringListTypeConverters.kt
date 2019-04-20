package com.application.flickr.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

/**
 * Created by Harsh Jain on 21/04/19.
 */

class StringListTypeConverters {
    @TypeConverter
    fun stringToStringList(data: String): List<String>? {
        val listType = object : TypeToken<ArrayList<String>>() {

        }.type
        return Gson().fromJson<List<String>>(data, listType)
    }

    @TypeConverter
    fun stringListToString(stringList: List<String>?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}