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

// Swapped from ScalarsFactory

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
//val adapter: JsonAdapter<PictureOfDay> = moshi.adapter(PictureOfDay::class.java)
//val pictureOfTheDay = adapter.fromJson()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

//private val jsonAdapter: JsonAdapter<PictureOfDay> = moshi.adapter()<PictureOfDay>()

//val pictureOfTheDay = jsonAdapter.fromJson(json)

// It is more important to implement interfaces before classes than vice versa.  That is why there is no class declaration.

interface PictureOfDayService {
    // Protocols to be implemented by the PODSI?
    // 1. GET the JSON Response String

    @GET("apod?api_key=${BuildConfig.NASA_KEY}")
    suspend fun getPictureOfDay(): PictureOfDay

}

// All I know is that this app is small and this object allows us to use the interface freely throughout the app (which, now I think about it,
// makes sense to do here.

// Lazy is mainly used when you want to access some read-only property because the same object is accessed throughout.  It's initialized
// when first used.
// This object is used as a Singleton.  Best to avoid this, though.  They don't have a state to track.
object PictureOfDayAPI {
    val retrofitService: PictureOfDayService by lazy {
        // this returns an object that implements the api service (so not all your classes
        // have to implement the interface).  Singletons are generally bad, but I am not scaling this project in the future, so it's whatev.
        retrofit.create(PictureOfDayService::class.java)
    }
}

