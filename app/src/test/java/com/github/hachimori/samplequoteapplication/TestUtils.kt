package com.github.hachimori.samplequoteapplication

import com.github.hachimori.samplequoteapplication.io.appmodels.Quote
import com.github.hachimori.samplequoteapplication.usecases.GetQuoteUsecase
import kotlinx.coroutines.flow.flow
import org.mockito.Mockito.`when`

/**
 * This mock returns;
 *   - Quote(text="quote_en_0") for the 1st getQuote("en")
 *   - Quote(text="quote_en_1") for the 2nd getQuote("en")
 *   - Quote(text="quote_en_2") for the 3rd getQuote("en")
 *   - ... and so on
 */
fun GetQuoteUsecase.setupMock() {
    var nCall = 0

    `when`(getQuote("en")).thenReturn(
        flow {
            emit(Quote(text = "quote_en_$nCall"))
            ++nCall
        }
    )
}
