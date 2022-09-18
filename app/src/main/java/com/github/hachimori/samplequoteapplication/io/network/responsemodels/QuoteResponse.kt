package com.github.hachimori.samplequoteapplication.io.network.responsemodels

import androidx.annotation.Keep
import com.github.hachimori.samplequoteapplication.io.appmodels.Quote
import com.google.gson.annotations.SerializedName

/**
 * API response model which stores quote information
 */
@Keep
data class QuoteResponse(
    @SerializedName("data")
    val data: QuoteResponseData
) {
    fun mapToQuote() = Quote(text = data.text)
}

@Keep
data class QuoteResponseData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
