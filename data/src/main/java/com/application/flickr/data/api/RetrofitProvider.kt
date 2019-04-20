package com.application.flickr.data.api

import android.app.Application
import com.application.flickr.data.api.util.LiveDataCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Harsh Jain on 18/04/19.
 */

object RetrofitProvider {

    const val BASE_URL = "https://api.flickr.com/services/"

    fun provideDefaultRetrofit(context: Application, showNetworkLogs: Boolean): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(provideOkHttpClient(context, showNetworkLogs))
        .build()

    private fun provideOkHttpClient(context: Application, showNetworkLogs: Boolean): OkHttpClient {
        val cacheSize = 20 * 1024 * 1024L // 20 MB
        val cache = Cache(context.cacheDir, cacheSize)

        val builder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        if (showNetworkLogs) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        builder.cache(cache)
        return builder.build()
    }
}