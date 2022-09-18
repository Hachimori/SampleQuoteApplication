package com.github.hachimori.samplequoteapplication

import androidx.compose.runtime.mutableStateOf
import com.github.hachimori.samplequoteapplication.io.appmodels.Quote
import com.github.hachimori.samplequoteapplication.ui.quote.QuoteUiState
import com.github.hachimori.samplequoteapplication.ui.quote.QuoteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.`when`

/**
 * This mock returns;
 *   - QuoteUiState(Quote(text="quote_en_0"), isLoading = false, hasError = false) for the 1st getQuote("en")
 *   - QuoteUiState(Quote(text="quote_en_1"), isLoading = false, hasError = false) for the 2nd getQuote("en")
 *   - QuoteUiState(Quote(text="quote_en_2"), isLoading = false, hasError = false) for the 3rd getQuote("en")
 *   - ... and so on
 *
 *   - and the same goes to getQuote("de")
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun QuoteViewModel.setupMock() {
    val mockedUiState = mutableStateOf(QuoteUiState())
    `when`(uiState).thenReturn(mockedUiState)

    var nCallEn = 0
    `when`(getQuote("en")).then {
        runTest {
            mockedUiState.value = QuoteUiState(
                quote = Quote("quote_en_$nCallEn"),
                isLoading = false,
                hasError = false
            )
            ++nCallEn
        }
    }

    var nCallDe = 0
    `when`(getQuote("de")).then {
        runTest {
            mockedUiState.value = QuoteUiState(
                quote = Quote("quote_de_$nCallDe"),
                isLoading = false,
                hasError = false
            )
            ++nCallDe
        }
    }
}
