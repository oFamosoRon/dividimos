package com.ofamosoron.dividimos

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ofamosoron.dividimos.ui.homeScreen.HomeScreen
import com.ofamosoron.dividimos.ui.homeScreen.HomeScreenTags.TOP_BAR
import com.ofamosoron.dividimos.ui.homeScreen.HomeScreenTags.TOP_BAR_ACTIONS
import com.ofamosoron.dividimos.ui.homeScreen.HomeScreenTags.TOP_BAR_LABEL
import com.ofamosoron.dividimos.ui.theme.DividimosTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenFeature {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun topBarTitleIsDisplayed() {
        testRule.setContent {
            DividimosTheme {
                HomeScreen()
            }
        }

        testRule.onNodeWithTag(TOP_BAR)
            .assertIsDisplayed()

        testRule.onNodeWithTag(TOP_BAR_LABEL)
            .assertTextContains("Dividimos")
            .assertIsDisplayed()
    }

    @Test
    fun isActionMenuIconVisible() {
        testRule.setContent {
            DividimosTheme {
                HomeScreen()
            }
        }

        testRule.onNodeWithTag(TOP_BAR_ACTIONS)
            .assertHasClickAction()
            .assertIsDisplayed()
    }

    @Test
    fun whenClickingActionMenuIconExpandsOrCollapseDropDownMenu() {
        testRule.setContent {
            DividimosTheme {
                HomeScreen()
            }
        }

        testRule.onNodeWithTag(TOP_BAR_ACTIONS)
            .performClick()

        testRule.onNodeWithText("Taxa de serviço")
            .assertIsDisplayed()

        testRule.onNodeWithText("Couvert Artistico")
            .assertIsDisplayed()

        testRule.onNodeWithText("Fechar a conta")
            .assertIsDisplayed()

        testRule.onNodeWithTag(TOP_BAR_ACTIONS)
            .performClick()
    }
}