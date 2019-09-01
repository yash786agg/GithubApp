package com.app.api

sealed class NetworkState<T>(val errorCode: Int? = null) {
    class Success<T> : NetworkState<T>()
    class Loading<T> : NetworkState<T>()
    class Error<T>(errorCode: Int) : NetworkState<T>(errorCode)
}
