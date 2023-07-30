package com.ofamosoron.dividimos.homeScreen.topBar

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ofamosoron.dividimos.homeScreen.BaseHomeScreenTest
import com.ofamosoron.dividimos.ui.homeScreen.HomeScreenTags
import com.ofamosoron.dividimos.ui.homeScreen.HomeScreenTopBar
import com.ofamosoron.dividimos.ui.theme.DividimosTheme
import org.junit.Test

class HomeScreenTopBarFeature: BaseHomeScreenTest() {

    @Test
    fun topBarTitleIsDisplayed() {
        testRule.setContent {
            DividimosTheme {
                HomeScreenTopBar()
            }
        }

        testRule.onNodeWithTag(HomeScreenTags.TOP_BAR_LABEL)
            .assertTextContains("Dividimos")
            .assertIsDisplayed()
    }

    @Test
    fun isActionMenuIconVisible() {
        testRule.setContent {
            DividimosTheme {
                HomeScreenTopBar()
            }
        }

        testRule.onNodeWithTag(HomeScreenTags.TOP_BAR_ACTIONS)
            .assertHasClickAction()
            .assertIsDisplayed()
    }

    @Test
    fun whenClickingActionMenuIconExpandsOrCollapseDropDownMenu() {
        testRule.setContent {
            DividimosTheme {
                HomeScreenTopBar()
            }
        }

        testRule.onNodeWithTag(HomeScreenTags.TOP_BAR_ACTIONS)
            .performClick()

        testRule.onNodeWithText("Taxa de serviço")
            .assertIsDisplayed()

        testRule.onNodeWithText("Couvert Artistico")
            .assertIsDisplayed()

        testRule.onNodeWithText("Fechar a conta")
            .assertIsDisplayed()

        testRule.onNodeWithTag(HomeScreenTags.TOP_BAR_ACTIONS)
            .performClick()
    }
}