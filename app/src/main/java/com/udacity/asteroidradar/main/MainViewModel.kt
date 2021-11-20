package com.udacity.asteroidradar.main

import android.graphics.Picture
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.networkLayer.PictureOfDayAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    //internal
    private val _pictureResponse = MutableLiveData<PictureOfDay>()

    // exposed/public
    val pictureResponse: LiveData<PictureOfDay>
        get() = _pictureResponse


    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    init {
        getPictureOfTheDay()
    }

    private fun getPictureOfTheDay() {
        viewModelScope.launch {
            try {
                val pictureOfDay = PictureOfDayAPI.retrofitService.getPictureOfDay()
                _pictureResponse.value = pictureOfDay
            } catch (e: Exception) {
                print("Failure: ${e.message}")

            }
        }
    }

//    fun configure(pictureOfDay: PictureOfDay) {
//        //binding.shoe = shoe
//        binding
//    }

}