package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.*
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.networkLayer.AsteroidAPI
import com.udacity.asteroidradar.networkLayer.PictureOfDayAPI
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database =  AsteroidDatabase.getDatabase(application)
    private val asteroidsRepository = AsteroidRepository(database)

    private val _pictureResponse = MutableLiveData<PictureOfDay>()
    val pictureResponse: LiveData<PictureOfDay>
        get() = _pictureResponse

    private val _asteroidResponse = MutableLiveData<List<Asteroid>>()
    val asteroidResponse: LiveData<List<Asteroid>>
        get() = _asteroidResponse

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    // create an asteroids variable that holds onto livedata asteroids from the viewmodel

    init {
        viewModelScope.launch {
            getPictureOfTheDay()
            asteroidsRepository.refreshAsteroids()
            _asteroidResponse.value = asteroidsRepository.getAsteroids()
        }
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

}