package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.networkLayer.AsteroidAPI
import com.udacity.asteroidradar.networkLayer.PictureOfDayAPI
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val _pictureResponse = MutableLiveData<PictureOfDay>()
    val pictureResponse: LiveData<PictureOfDay>
        get() = _pictureResponse

    private val _asteroidResponse = MutableLiveData<List<Asteroid>>()
    val asteroidResponse: LiveData<List<Asteroid>>
        get() = _asteroidResponse

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    init {
        getPictureOfTheDay()
        getAsteroidPayload()
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

    private fun getAsteroidPayload() {
        viewModelScope.launch {
            try {
                val asteroidPayload = AsteroidAPI.retrofitService.getAsteroids()
                print(asteroidPayload.toString())
                _asteroidResponse.value = asteroidPayload
            } catch(exception: Exception) {
                print("Failure: ${exception.message}")
            }
        }
    }
}