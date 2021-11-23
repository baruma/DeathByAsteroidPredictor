package com.udacity.asteroidradar.networkLayer

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY
private const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofitAsteroidService: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AsteroidService {
    @GET("feed?start_date=2015-09-07&end_date=2015-09-08&api_key=${BuildConfig.NASA_KEY}")
    suspend fun getAsteroids(): List<Asteroid>
}

object AsteroidAPI {
    val retrofitService: AsteroidService by lazy {
        retrofitAsteroidService.create(AsteroidService::class.java)
    }
}