package com.github.hachimori.samplequoteapplication.ui.quote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.hachimori.samplequoteapplication.io.appmodels.Quote
import com.github.hachimori.samplequoteapplication.usecases.GetQuoteUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.text.Typography.quote

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getQuoteUsecase: GetQuoteUsecase
) : ViewModel() {

    var uiState by mutableStateOf(QuoteUiState())
        private set

    fun getQuote(lang: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, hasError = false)

            uiState = try {
                val quote = getQuoteUsecase.getQuote(lang).single()
                uiState.copy(quote = quote, isLoading = false)
            } catch (e: Exception) {
                Timber.e("Failed in retrieving quote: ${e.message}")
                uiState.copy(isLoading = false, hasError = true)
            }
        }
    }
}

data class QuoteUiState(
    val quote: Quote? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false
)
