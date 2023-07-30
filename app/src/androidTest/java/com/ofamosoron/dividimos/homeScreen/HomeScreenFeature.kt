package com.ofamosoron.dividimos.homeScreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import com.ofamosoron.dividimos.ui.homeScreen.HomeScreen
import com.ofamosoron.dividimos.ui.homeScreen.HomeScreenTags
import com.ofamosoron.dividimos.ui.theme.DividimosTheme
import org.junit.Test

class HomeScreenFeature: BaseHomeScreenTest() {

    @Test
    fun hasVisibleTopBar() {
        testRule.setContent {
            DividimosTheme {
                HomeScreen()
            }
        }

        testRule.onNodeWithTag(HomeScreenTags.TOP_BAR)
            .assertIsDisplayed()
    }
}