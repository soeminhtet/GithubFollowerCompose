package com.smh.githubfollowercompose.utility

sealed class ResponseData<out T> {
    data class Success<T>(val data : T) : ResponseData<T>()
    data class Failed(val status : Int? = null, val message : String? = null) : ResponseData<Nothing>()
    data class Exception(val exception: kotlin.Exception) : ResponseData<Nothing>()
}