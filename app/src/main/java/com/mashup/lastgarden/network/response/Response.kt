package com.mashup.lastgarden.network.response

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse

data class Response<T>(
    val code: Int?,
    val data: T? = null,
    val paging: Paging? = null,
    val error: Error? = null,
    val status: String? = null
)

data class Paging(val before: String? = null, val after: String? = null, val total: Long?)

data class Error(
    val code: Int? = null,
    val message: String? = null
)

data class DataResponse<T>(val data: T? = null)
data class ErrorResponse(val error: Error? = null)

typealias NetworkDataResponse<T> = NetworkResponse<DataResponse<T>, ErrorResponse>

fun <T : Any> NetworkDataResponse<T>.onErrorReturnData(item: T): T = when (this) {
    is NetworkResponse.Success -> body.data ?: item
    is NetworkResponse.ServerError -> {
        Log.e(
            "NetworkResponse",
            "onErrorReturn: ServerError(${this.code}), ${this.body?.error?.code}, ${this.body?.error?.message}"
        )
        item
    }
    is NetworkResponse.NetworkError -> {
        Log.e("NetworkResponse", "onErrorReturn: NetworkError", this.error)
        item
    }
    is NetworkResponse.UnknownError -> {
        Log.e("NetworkResponse", "onErrorReturn: UnknownError(${this.code})", this.error)
        item
    }
}

fun <T : Any> NetworkDataResponse<T>.onErrorReturnDataNull(): T? = when (this) {
    is NetworkResponse.Success -> body.data
    is NetworkResponse.ServerError -> {
        Log.e(
            "NetworkResponse",
            "onErrorReturn: ServerError(${this.code}), ${this.body?.error?.code}, ${this.body?.error?.message}"
        )
        null
    }
    is NetworkResponse.NetworkError -> {
        Log.e("NetworkResponse", "onErrorReturn: NetworkError", this.error)
        null
    }
    is NetworkResponse.UnknownError -> {
        Log.e("NetworkResponse", "onErrorReturn: UnknownError(${this.code})", this.error)
        null
    }
}
