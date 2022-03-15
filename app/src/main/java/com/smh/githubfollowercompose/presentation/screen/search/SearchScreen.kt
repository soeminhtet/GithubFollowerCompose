package com.smh.githubfollowercompose.presentation.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smh.githubfollowercompose.presentation.component.BottomNavigationBar
import com.smh.githubfollowercompose.presentation.component.SearchTextField
import com.smh.githubfollowercompose.R
import com.smh.githubfollowercompose.ShareViewModel
import com.smh.githubfollowercompose.presentation.screen.Screen
import com.smh.githubfollowercompose.ui.theme.Accent
import com.smh.githubfollowercompose.ui.theme.Background
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun SearchScreen(
    navController: NavController,
    shareViewModel: ShareViewModel,
    viewModel: SearchViewModel = hiltViewModel()
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        SearchMainComponent(
            paddingValues = paddingValues,
            searchName = viewModel.searchName.value,
            searchError = viewModel.searchError.value,
            onSearchNameChange = { viewModel.onSearchNameChange(it) },
            onSearchClick = {
                if (viewModel.onSearch()) {
                    val name = viewModel.searchName.value
                    viewModel.clearSearchName()
                    shareViewModel.changeUsername(name)
                    navController.navigate(Screen.ListScreen)
                }
            }
        )
    }
}

@Composable
fun SearchMainComponent(
    paddingValues : PaddingValues,
    searchName : String,
    searchError : String,
    onSearchNameChange : (String) -> Unit,
    onSearchClick : () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize()
            .background(MaterialTheme.colors.Background.copy(alpha = 0.9f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_github_logo),
            contentDescription = stringResource(R.string.githubLogo),
            tint = MaterialTheme.colors.Accent
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.github),
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.Accent
        )
        Text(
            text = stringResource(R.string.followers),
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.Accent
        )
        Spacer(modifier = Modifier.height(50.dp))

        SearchTextField(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .width(300.dp),
            searchName = searchName,
            searchError = searchError,
            onSearchNameChange = {
                onSearchNameChange(it)
            },
            onSearchClick = {
                onSearchClick()
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onSearchClick() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
            modifier = Modifier
                .width(300.dp)
                .height(55.dp)
                .testTag("SearchBtn")
        ) {
            Text(stringResource(R.string.getFollowers))
        }
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SearchMainComponentPreview() {
    SearchMainComponent(
        paddingValues = PaddingValues(8.dp),
        searchName = "",
        searchError = "error",
        onSearchNameChange = {

        },
        onSearchClick = {}
    )
}