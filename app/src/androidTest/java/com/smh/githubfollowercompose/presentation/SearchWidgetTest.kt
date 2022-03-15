package com.smh.githubfollowercompose.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.smh.githubfollowercompose.presentation.component.SearchTextField
import org.junit.Rule
import org.junit.Test

class SearchWidgetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun openSearchWidget_addInputText_assertInputText() {
        val searchName = mutableStateOf("")
        composeTestRule.setContent {
            SearchTextField(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .width(300.dp),
                searchName = searchName.value,
                searchError = "",
                onSearchNameChange = { searchName.value = it },
                onSearchClick = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("SearchTextFiled")
            .performTextInput("soeminhtet")
        composeTestRule.onNodeWithContentDescription("SearchTextFiled")
            .assertTextEquals("soeminhtet")
    }

    @Test
    fun openSearchWidget_addInputText_pressCloseButtonOnce_assertEmptyInputText() {
        val searchName = mutableStateOf("")
        composeTestRule.setContent {
            SearchTextField(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .width(300.dp),
                searchName = searchName.value,
                searchError = "",
                onSearchNameChange = { searchName.value = it },
                onSearchClick = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("SearchTextFiled")
            .performTextInput("soeminhtet")
        composeTestRule.onNodeWithContentDescription("SearchClearBtn")
            .performClick()
        composeTestRule.onNodeWithContentDescription("SearchTextFiled")
            .assertTextContains("")
    }
}