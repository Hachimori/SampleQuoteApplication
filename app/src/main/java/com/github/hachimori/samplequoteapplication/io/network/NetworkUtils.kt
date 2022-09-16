package com.github.hachimori.samplequoteapplication.io.network

import retrofit2.Response
import timber.log.Timber
import java.io.IOException

/**
 * A helper function for retrofit2.Response which returns response body
 * or throws IOException in case of invalid response.
 *
 * @return Response body
 */
fun <T> Response<T>.getBodyOrThrow(): T {
    val body = body()
    if (!isSuccessful || body == null) {
        Timber.e("Bad response from API %s", code())
        throw IOException(code().toString())
    }
    return body
}
