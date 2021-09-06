package com.dice.shared.utils.network

import retrofit2.Response

fun <T> Response<T>.dataResponse(): T = body()
    ?: throw IllegalStateException("Unexpected response, please try later")