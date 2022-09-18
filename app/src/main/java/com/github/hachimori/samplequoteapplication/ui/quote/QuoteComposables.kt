package com.github.hachimori.samplequoteapplication.ui.quote

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.hachimori.samplequoteapplication.R
import com.github.hachimori.samplequoteapplication.io.appmodels.Quote

sealed class Language(
    @StringRes val stringRes: Int,
    val code: String
) {
    class English : Language(R.string.english, "en")
    class German : Language(R.string.german, "de")
    class French : Language(R.string.french, "fr")
    class Dutch : Language(R.string.dutch, "nl")
}

@Composable
fun QuoteScreen() {
    val quoteViewModel = hiltViewModel<QuoteViewModel>()
    var lang by remember { mutableStateOf(Language.English() as Language) }
    val uiState = quoteViewModel.uiState.value

    if (uiState.quote == null) {
        // Load initial quote on startup
        LaunchedEffect(Unit) {
            quoteViewModel.getQuote(lang.code)
        }
    }

    QuoteContent(
        quote = uiState.quote,
        isLoading = uiState.isLoading,
        hasError = uiState.hasError,
        onChooseLanguage = { newLang ->
            lang = newLang
        },
        onGetQuote = {
            quoteViewModel.getQuote(lang.code)
        }
    )
}

@Composable
fun QuoteContent(
    quote: Quote?,
    isLoading: Boolean,
    hasError: Boolean,
    onChooseLanguage: (Language) -> Unit,
    onGetQuote: () -> Unit
) {
    if (isLoading) {
        LinearProgressIndicator(
            modifier = Modifier
                .height(5.dp)
                .fillMaxWidth()
        )
    }

    Column {
        QuoteText(
            modifier = Modifier
                .heightIn(min = 250.dp)
                .padding(vertical = 16.dp),
            isLoading = isLoading,
            text = quote?.text
        )
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                QuoteGetButton(
                    modifier = Modifier,
                    onGetQuote = onGetQuote
                )
                QuoteLanguagePicker(
                    modifier = Modifier.width(200.dp),
                    onSelectLanguage = onChooseLanguage
                )
            }
            QuoteError(
                modifier = Modifier.padding(horizontal = 16.dp),
                hasError = hasError
            )
        }
    }
}

@Composable
fun QuoteText(
    modifier: Modifier,
    isLoading: Boolean,
    text: String?
) {
    Column(modifier = modifier) {
        AnimatedVisibility(
            visible = !isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                text = text ?: ""
            )
        }
    }
}

@Composable
fun QuoteGetButton(
    modifier: Modifier,
    onGetQuote: () -> Unit
) {
    Button(
        modifier = modifier,
        content = {
            Text(stringResource(R.string.get_a_new_quote))
        },
        onClick = {
            onGetQuote()
        }
    )
}

@Composable
fun QuoteError(
    modifier: Modifier,
    hasError: Boolean
) {
    if (hasError) {
        Text(
            modifier = modifier,
            text = stringResource(R.string.network_error),
            color = Color.Red
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuoteLanguagePicker(
    modifier: Modifier,
    onSelectLanguage: (Language) -> Unit
) {
    val languages = listOf(Language.English(), Language.German(), Language.French(), Language.Dutch())
    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf(languages[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = modifier,
            readOnly = true,
            value = stringResource(selectedLanguage.stringRes),
            onValueChange = { },
            label = { Text(stringResource(R.string.select_language)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            languages.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onSelectLanguage(selectionOption)
                        selectedLanguage = selectionOption
                        expanded = false
                    }
                ) {
                    Text(text = stringResource(selectionOption.stringRes))
                }
            }
        }
    }
}
