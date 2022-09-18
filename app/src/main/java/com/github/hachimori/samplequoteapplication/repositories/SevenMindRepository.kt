package com.github.hachimori.samplequoteapplication.repositories

import com.github.hachimori.samplequoteapplication.io.network.SevenMindApiService
import com.github.hachimori.samplequoteapplication.io.network.responsemodels.QuoteResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A class which deals with 7Mind-related data
 */
@Singleton
class SevenMindRepository @Inject constructor(
    private val sevenMindApiService: SevenMindApiService
) {

    /**
     * Get quote
     *
     * @param lang Language in which the quote is presented ("nl", "fr", "en" or "de")
     * @param uid ID of quote
     * @return Quote retrieved from API
     */
    suspend fun getQuote(lang: String, uid: String): Response<QuoteResponse> =
        sevenMindApiService.getQuote(lang, uid)
}
