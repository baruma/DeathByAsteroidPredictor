package com.udacity.asteroidradar

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.networkLayer.AsteroidAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

// Constructor Injection keeps the class from having a reference to objects - which could in turn, could create leaks.
class AsteroidRepository(private val database: AsteroidDatabase) {

    // Right so we have our database that our RecyclerView grabs data from as is.
    // My database has been made, but now it's time to use it for our offline cache - we use the Repository Pattern for this.
    // Repositories handle data in the offline cache - the Room DB does not do this as it has no logic
    // for handling the offline cache.


    // We're making a call to the DB to save new Asteroids to the DB.
    // DB's in Android are stored in the FileSystem aka Disk.  You will need DiskIO.  It is slow.  Compared to saving to RAM that is.
    // These kind of funcs are UI blocking.

    // repositories are caches.

    suspend fun getAsteroids() = database.asteroidDAO.getAllAsteroids()

    suspend fun refreshAsteroids() {
        val simpleDateFormat= SimpleDateFormat("yyyy-MM-dd")
        val currentDate: String = simpleDateFormat.format(Date())

        withContext(Dispatchers.IO) {
            try {
                val asteroidPayload = AsteroidAPI.retrofitService.getAsteroids(currentDate)
                val jsonObject: JSONObject = JSONObject(asteroidPayload)

                database.asteroidDAO.insertAsteroids(parseAsteroidsJsonResult(jsonObject))

            } catch(exception: Exception) {
                print("Failure: ${exception.message}")
            }
        }
    }

        // This function needs to present asteroids in recyclerview
//    suspend fun loadAsteroidPayload() {
////        viewModelScope.launch {
////           _asteroidResponse.value =  database.getAllAsteroids()
//        //}
//    }

}

// The cache is just livedata.  LiveData is being used as an async wrapper.
// You're using the cache to look for duplicate data since you make the workmanager call every day
// The fiinal source of code is not  the repository.

// The cache is going to make network calls in order to update the database which is our final source of truth.