package com.github.hachimori.samplequoteapplication.tests

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.hachimori.samplequoteapplication.HiltTestActivity
import com.github.hachimori.samplequoteapplication.R
import com.github.hachimori.samplequoteapplication.setupMock
import com.github.hachimori.samplequoteapplication.ui.quote.QuoteScreen
import com.github.hachimori.samplequoteapplication.ui.quote.QuoteViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class QuoteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @BindValue
    val fakeQuoteViewModel: QuoteViewModel = mock(QuoteViewModel::class.java)

    @Before
    fun setup() {
        fakeQuoteViewModel.setupMock()
    }

    /**
     * Test {@link QuoteScreen()}
     *   1. Get 2 English quotes, and check if quotes (from mocked ViewModel) are displayed
     *   2. Get 2 German quotes, and check if quotes (from mocked ViewModel) are displayed
     */
    @Test
    fun testQuoteContent() {
        composeTestRule.setContent {
            QuoteScreen()
        }

        // Check initial English quote
        composeTestRule
            .onNodeWithText("quote_en_0")
            .assertIsDisplayed()

        // Get English quote
        composeTestRule
            .onNodeWithText(getText(R.string.get_a_new_quote))
            .performClick()

        // Check English quote
        composeTestRule
            .onNodeWithText("quote_en_1")
            .assertIsDisplayed()

        // Get another English quote
        composeTestRule
            .onNodeWithText(getText(R.string.get_a_new_quote))
            .performClick()

        // Check 2nd English quote
        composeTestRule
            .onNodeWithText("quote_en_2")
            .assertIsDisplayed()

        // Switch language to German
        composeTestRule
            .onNodeWithText(getText(R.string.select_language))
            .performClick()

        composeTestRule
            .onNodeWithText(getText(R.string.german))
            .performClick()

        // Get German quote
        composeTestRule
            .onNodeWithText(getText(R.string.get_a_new_quote))
            .performClick()

        // Check German quote
        composeTestRule
            .onNodeWithText("quote_de_0")
            .assertIsDisplayed()

        // Get another German quote
        composeTestRule
            .onNodeWithText(getText(R.string.get_a_new_quote))
            .performClick()

        // Check 2nd German quote
        composeTestRule
            .onNodeWithText("quote_de_1")
            .assertIsDisplayed()
    }

    private fun getText(@StringRes textRes: Int, vararg arg: Any) =
        (ApplicationProvider.getApplicationContext() as Context).resources.getString(textRes, *arg)
}
