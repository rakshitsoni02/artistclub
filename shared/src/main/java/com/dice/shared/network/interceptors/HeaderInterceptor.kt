package com.dice.shared.network.interceptors

import com.dice.shared.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Header interceptor helps to get json format response from musicbrainz
 */
internal class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
         val agent =
            "${BuildConfig.LIBRARY_PACKAGE_NAME}/${BuildConfig.BUILD_TYPE}"
        request = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("User-Agent", agent)
            .build()
        return chain.proceed(request)
    }

    private companion object {

    }
}