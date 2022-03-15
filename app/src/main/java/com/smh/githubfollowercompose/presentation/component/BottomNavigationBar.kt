package com.smh.githubfollowercompose.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.smh.githubfollowercompose.R
import com.smh.githubfollowercompose.presentation.screen.Screen
import com.smh.githubfollowercompose.ui.theme.BottomNav

@Composable
fun BottomNavigationBar(navController : NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val searchColor by animateColorAsState(
        targetValue = if (currentRoute == Screen.SearchScreen) MaterialTheme.colors.BottomNav else Color.Gray
    )
    val favouriteColor by animateColorAsState(
        targetValue = if (currentRoute == Screen.FavouriteScreen) MaterialTheme.colors.BottomNav else Color.Gray
    )

    BottomNavigation(
        modifier = Modifier.navigationBarsWithImePadding(),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.secondary
    ) {
        BottomNavigationItem(
            selected = currentRoute == Screen.SearchScreen,
            onClick = {
                navController.navigate(Screen.SearchScreen) {
                    popUpTo(Screen.SearchScreen) { inclusive = true}
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.searchBarIcon),
                    tint = searchColor
                )
            },
            label = {
                Text(text = stringResource(R.string.search), color = searchColor)
            }
        )

        BottomNavigationItem(
            selected = currentRoute == Screen.FavouriteScreen,
            onClick = {
                navController.navigate(Screen.FavouriteScreen) {
                    popUpTo(Screen.FavouriteScreen) { inclusive = true }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.favBarIcon),
                    tint = favouriteColor
                )
            },
            label = {
                Text(text = stringResource(id = R.string.favourites), color = favouriteColor)
            }
        )
    }
}