package com.example.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.api.NetworkResponce
import com.example.retrofit.api.RetrofitInstance
import com.example.retrofit.api.WeatherModel
import kotlinx.coroutines.launch

class ViewModel:ViewModel() {


    val weatherApi = RetrofitInstance.weatherApi

    private val _weatherResult = MutableLiveData<NetworkResponce<WeatherModel>>()
    val weaterResult : LiveData<NetworkResponce<WeatherModel>> = _weatherResult

    fun getData(city: String){

            _weatherResult.value = NetworkResponce.Loading

        try {

            viewModelScope.launch {
                val response = weatherApi.getWeather(Apikey.apiKey, city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponce.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponce.Error("Data in not Loading")
                }
            }
        }
        catch (e: Exception){
            _weatherResult.value = NetworkResponce.Error("Data in not Loading")
        }
    }
}