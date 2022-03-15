package com.smh.githubfollowercompose.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smh.githubfollowercompose.ui.theme.Accent

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    searchName : String,
    searchError : String,
    onSearchNameChange : (String) -> Unit,
    onSearchClick : () -> Unit
) {
    val clearIconColor = animateColorAsState(
        if (searchName.isEmpty()) Color.Transparent else MaterialTheme.colors.Accent
    )

    Column(modifier = modifier.semantics { contentDescription = "SearchWidget" }) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().semantics { contentDescription = "SearchTextFiled" },
            value = searchName,
            onValueChange = {
                onSearchNameChange(it)
            },
            placeholder = {
                Text("Search...")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                placeholderColor = Color.Gray,
                cursorColor = MaterialTheme.colors.Accent,
                focusedBorderColor = MaterialTheme.colors.Accent,
                textColor = MaterialTheme.colors.Accent,
                unfocusedBorderColor = Color.Gray
            ),
            shape = RoundedCornerShape(size = 10.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchClick() }),
            trailingIcon = {
                IconButton(
                    modifier = Modifier.semantics { contentDescription = "SearchClearBtn" },
                    onClick = { onSearchNameChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = clearIconColor.value
                    )
                }
            },
            singleLine = true,
            isError = searchError.isNotEmpty()
        )

        if (searchError.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                text = searchError,
                fontSize = MaterialTheme.typography.caption.fontSize,
                color = MaterialTheme.colors.error,
                fontWeight = FontWeight.W300,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        searchName = "",
        searchError = "error",
        onSearchNameChange = {},
        onSearchClick = {}
    )
}