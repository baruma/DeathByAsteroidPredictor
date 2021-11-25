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

// https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY
private const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"

//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()

val retrofitAsteroidService: Retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// Do not hardcode date - use Calendar class for today's date and use simple date format.
// Use API_QUERY_DATE_FORMAT
//     @GET("feed?start_date=2015-09-07&end_date=2015-09-08&api_key=${BuildConfig.NASA_KEY}")


// Get the current date and the next 7 dates should be generated automatically
//https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY
interface AsteroidService {
    @GET("feed?api_key=${BuildConfig.NASA_KEY}")
  //  suspend fun getAsteroids(): String
  suspend fun getAsteroids(@Query("start_date") startDate: String): String
}

object AsteroidAPI {
    val retrofitService: AsteroidService by lazy {
        retrofitAsteroidService.create(AsteroidService::class.java)
    }
}

// https://api.nasa.gov/neo/rest/v1/feed?start_date=2021-11-24&api_key=yQBss5r6aSAbVImDGjhOe3MvPNr788PbIpbdY91o