package com.github.hachimori.samplequoteapplication.ui.quote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.hachimori.samplequoteapplication.io.appmodels.Quote
import timber.log.Timber

@Composable
fun QuoteScreen() {
    val quoteViewModel = hiltViewModel<QuoteViewModel>()
    val uiState = quoteViewModel.uiState

    QuoteContent(
        quote = uiState.quote,
        isLoading = uiState.isLoading,
        hasError = uiState.hasError,
        onGetQuote = {
            quoteViewModel.getQuote("en")
        }
    )
}

@Composable
fun QuoteContent(
    quote: Quote?,
    isLoading: Boolean,
    hasError: Boolean,
    onGetQuote: () -> Unit
) {
    Column {
        QuoteLoading(isLoading)
        QuoteText(quote?.text)
        QuoteGetButton(onGetQuote = onGetQuote)
        QuoteError(hasError)
    }
}

@Composable
fun QuoteLoading(isLoading: Boolean) {
    if (isLoading) {
        Text("Loading")
    }
}

@Composable
fun QuoteText(text: String?) {
    Text(text ?: "")
}

@Composable
fun QuoteGetButton(onGetQuote: () -> Unit) {
    Button(
        content = {
            Text("Get new quote")
        },
        onClick = {
            onGetQuote()
        }
    )
}

@Composable
fun QuoteError(hasError: Boolean) {
    if (hasError) {
        Text("Error occurred")
    }
}