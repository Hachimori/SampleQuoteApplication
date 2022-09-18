package com.github.hachimori.samplequoteapplication.tests

import com.github.hachimori.samplequoteapplication.MainDispatcherRule
import com.github.hachimori.samplequoteapplication.io.appmodels.Quote
import com.github.hachimori.samplequoteapplication.setupMock
import com.github.hachimori.samplequoteapplication.ui.quote.QuoteUiState
import com.github.hachimori.samplequoteapplication.ui.quote.QuoteViewModel
import com.github.hachimori.samplequoteapplication.usecases.GetQuoteUsecase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class QuoteViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    /**
     * Test {@link QuoteViewModel#getQuote()}
     *   - Get 2 English quotes, and check if UI state is properly set
     */
    @Test
    fun testGetQuote() = runTest {
        val mockedGetQuoteUsecase: GetQuoteUsecase = mock(GetQuoteUsecase::class.java)
        mockedGetQuoteUsecase.setupMock()

        val quoteViewModel = QuoteViewModel(mockedGetQuoteUsecase)

        // Get the 1st quote
        quoteViewModel.getQuote("en")

        // Check UI state after the 1st quote
        assertEquals(
            QuoteUiState(
                quote = Quote("quote_en_0"),
                isLoading = false,
                hasError = false
            ),
            quoteViewModel.uiState.value
        )

        // Get the 2nd quote
        quoteViewModel.getQuote("en")

        // Check UI state after the 2nd quote
        assertEquals(
            QuoteUiState(
                quote = Quote("quote_en_1"),
                isLoading = false,
                hasError = false
            ),
            quoteViewModel.uiState.value
        )
    }
}