package com.application.flickr.api.model

import com.application.flickr.api.model.Status.*

/**
 * Created by Harsh Jain on 18/04/19.
 */

data class Resource<out T>(val status: Status, val data: T?, val errorMessage: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val resource = other as Resource<*>?

        return if (status != resource?.status) {
            false
        } else (if (errorMessage != null) errorMessage == resource.errorMessage else resource.errorMessage == null)
                && if (data != null) data == resource.data else resource.data == null
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (data?.hashCode() ?: 0)
        result = 31 * result + (errorMessage?.hashCode() ?: 0)
        return result
    }
}