package com.github.hachimori.samplequoteapplication.io.network

import com.github.hachimori.samplequoteapplication.io.network.responsemodels.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An interface for accessing 7Mind API
 */
interface SevenMindApiService {

    /**
     * Get quote
     *
     * @param lang Language in which the quote is presented ("nl", "fr", "en" or "de")
     * @param uid ID of quote
     * @return Quote retrieved from API
     */
    @GET("mindful")
    suspend fun getQuote(
        @Query("lang") lang: String,
        @Query("uid") uid: String
    ): Response<QuoteResponse>

    companion object {
        const val SEVEN_MIND_API_ENDPOINT = "https://servicegateway.7mind.de/v2/"
    }
}
