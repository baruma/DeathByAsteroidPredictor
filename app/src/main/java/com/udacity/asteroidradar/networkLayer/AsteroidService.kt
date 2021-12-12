package com.udacity.asteroidradar.networkLayer

import com.udacity.asteroidradar.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"

val retrofitAsteroidService: Retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AsteroidService {
    @GET("feed?api_key=${BuildConfig.NASA_KEY}")
  //  suspend fun getAsteroids(): String
  suspend fun getAsteroids(@Query("start_date") startDate: String): String
}

//  This singleton oobject should be used by the repository - not the MVVM.
object AsteroidAPI {
    val retrofitService: AsteroidService by lazy {
        retrofitAsteroidService.create(AsteroidService::class.java)
    }
}