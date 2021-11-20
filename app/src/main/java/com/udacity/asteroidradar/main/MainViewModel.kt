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

    private val _pictureResponse = MutableLiveData<PictureOfDay>()
    val pictureResponse: LiveData<PictureOfDay>
        get() = _pictureResponse


    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    // Iniitalized so that this is called immediately.
    init {
        getPictureOfTheDay()
    }
// AFTER ADDING COROUTINES WE'VE BEEN HITTING ERRORS.
    private fun getPictureOfTheDay() {
        viewModelScope.launch {
            try {
                var photoResult = PictureOfDayAPI.retrofitService.getPictureOfDay()
                _pictureResponse.value = pictureResponse.value
            } catch (e: Exception) {
                print("Failure: ${e.message}")

            }
        }
        // getPictureOfDayStringData creates a call object.
        // enqeue starts a network request on a background thread.
//        PictureOfDayAPI.retrofitService.getPictureOfDay().enqueue(object : Callback<PictureOfDay> {
//            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
//
//

//                if (!response.isSuccessful) {
//                    print("UNSUCCESSFUL CALL")
//                }
//
//                print("SUCCESSFUL CALL")
//                print(response.body().toString())
//
//                _pictureResponse.value = response.body()
//            }
//
//            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
//                Log.e("ERROR", t.toString())
//            }
//
//        })
    }

}