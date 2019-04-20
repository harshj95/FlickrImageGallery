package com.application.flickr.data.api.model

import androidx.collection.ArrayMap
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.util.regex.Pattern

/**
 * Created by Harsh Jain on 18/04/19.
 */

@Suppress("unused") // T is used in extending classes
class ApiResponse<T> {
    private val code: Int
    val body: T?
    var errorMessage: String? = null
    private val responseUrl: String?
    private val links: MutableMap<String, String>

    val isSuccessful: Boolean
        get() = code in 200..299

    val nextPage: Int?
        get() {
            val next = links[NEXT_LINK] ?: return null
            val matcher = PAGE_PATTERN.matcher(next)
            if (!matcher.find() || matcher.groupCount() != 1) {
                return null
            }
            return try {
                Integer.parseInt(matcher.group(1))
            } catch (ex: NumberFormatException) {
                Timber.w("cannot parse next page from %s", next)
                null
            }

        }

    constructor(error: Throwable) {
        code = -1
        body = null
        responseUrl = null

        errorMessage = if (error is IOException) {
            "Please check your internet connection"
        } else {
            "Something went wrong"
        }

        links = mutableMapOf()
    }

    constructor(response: Response<T>) {
        code = response.code()
        body = response.body()
        responseUrl = response.raw().request().url().toString()
        if (isSuccessful) {
            errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()!!.string()
                } catch (ignored: IOException) {
                    Timber.e(ignored, "error while parsing response")
                }
            }
            if (message == null || message.trim { it <= ' ' }.isEmpty()) {
                message = response.message()
            }
            errorMessage = message
        }
        val linkHeader = response.headers().get("link")
        if (linkHeader == null) {
            links = mutableMapOf()
        } else {
            links = ArrayMap()
            val matcher = LINK_PATTERN.matcher(linkHeader)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
        }
    }

    companion object {
        private val LINK_PATTERN = Pattern
            .compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("page=(\\d)+")
        private const val NEXT_LINK = "next"
    }
}