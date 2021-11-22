package com.udacity.asteroidradar.networkLayer

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private val BASE_URL = "https://api.nasa.gov/neo/rest/v1/feed?"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofitAsteroidService: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AsteroidService {
    @GET
    suspend fun getAsteroids(): List<Asteroid>
}

object AsteroidAPI {
    val retrofitService: AsteroidService by lazy {
        retrofitPictureOfDay.create(AsteroidService::class.java)
    }
}