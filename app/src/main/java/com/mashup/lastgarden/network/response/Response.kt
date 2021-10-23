package com.mashup.lastgarden.network.response

data class Response<T>(
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