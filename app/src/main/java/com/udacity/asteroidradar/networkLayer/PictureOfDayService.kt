package com.udacity.asteroidradar.networkLayer

import android.graphics.Picture
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header


private val BASE_URL = "https://api.nasa.gov/planetary/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// Got a 403 error code earlier because I had a space between the '=' and Key.
interface PictureOfDayService {
    @GET("apod?api_key=${BuildConfig.NASA_KEY}")
    suspend fun getPictureOfDay(): PictureOfDay
}

object PictureOfDayAPI {
    val retrofitService: PictureOfDayService by lazy {
        retrofit.create(PictureOfDayService::class.java)
    }
}

