package com.udacity.asteroidradar.networkLayer

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.nasa.gov/planetary/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofitPictureOfDayService: Retrofit = Retrofit.Builder()
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
        retrofitPictureOfDayService.create(PictureOfDayService::class.java)
    }
}

