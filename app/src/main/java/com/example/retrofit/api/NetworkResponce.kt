package com.example.retrofit.api

sealed class NetworkResponce<out T> {
    data class Success<out T>(val data: T) : NetworkResponce<T>()
    data class Error(val messagee: String) : NetworkResponce<Nothing>()
    object Loading : NetworkResponce<Nothing>()
}