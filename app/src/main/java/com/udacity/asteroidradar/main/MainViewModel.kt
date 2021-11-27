package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidDAO
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.networkLayer.AsteroidAPI
import com.udacity.asteroidradar.networkLayer.PictureOfDayAPI
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class MainViewModel(val database: AsteroidDAO, application: Application) : AndroidViewModel(application) {

    // Here in the VM, we are impelementing a UIHander that will save the Asteroids to the Database.  We then have to call Asteroid
    // objects from the room database to shwo on the recyclerview

    private val _pictureResponse = MutableLiveData<PictureOfDay>()

    val pictureResponse: LiveData<PictureOfDay>
        get() = _pictureResponse

    private val _asteroidResponse = MutableLiveData<List<Asteroid>>()

    val asteroidResponse: LiveData<List<Asteroid>>
        get() = _asteroidResponse

//    private val savedDatabaseAsteroids = database.getAllAsteroids()

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status


    init {
        getPictureOfTheDay()
        saveAsteroidPayload()
    }

    private fun getPictureOfTheDay() {
        viewModelScope.launch {
            try {
                val pictureOfDay = PictureOfDayAPI.retrofitService.getPictureOfDay()
                _pictureResponse.value = pictureOfDay
            } catch (exception: Exception) {
                print("Failure: ${exception.message}")
            }
        }
    }

    private fun saveAsteroidPayload() {
        val simpleDateFormat= SimpleDateFormat("yyyy-MM-dd")
        val currentDate: String = simpleDateFormat.format(Date())

        viewModelScope.launch {
            try {
                val asteroidPayload = AsteroidAPI.retrofitService.getAsteroids(currentDate)
                val jsonObject: JSONObject = JSONObject(asteroidPayload)
                  _asteroidResponse.value = parseAsteroidsJsonResult(jsonObject)

                val asteroidsToBeSaved = database.insertAsteroids(_asteroidResponse.value!!)

            } catch(exception: Exception) {
                print("Failure: ${exception.message}")
            }
        }
    }

    private fun loadAsteroidPayload() {
        viewModelScope.launch {
            val asteroidsToLoad = database.getAllAsteroids()

        }
    }

}