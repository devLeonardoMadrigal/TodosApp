package com.example.todosapp.model

sealed class Response<out T> {
    data class Success<T>(val result : T) : Response<T>()
    data class Error(val msg : String) : Response<Nothing>()
}