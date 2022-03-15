package com.smh.githubfollowercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.smh.githubfollowercompose.presentation.screen.Screen.FavouriteScreen
import com.smh.githubfollowercompose.presentation.screen.Screen.ListScreen
import com.smh.githubfollowercompose.presentation.screen.Screen.SearchScreen
import com.smh.githubfollowercompose.presentation.screen.favourite.FavouriteScreen
import com.smh.githubfollowercompose.presentation.screen.list.ListScreen
import com.smh.githubfollowercompose.presentation.screen.search.SearchScreen
import com.smh.githubfollowercompose.ui.theme.Background
import com.smh.githubfollowercompose.ui.theme.GitHubFollowerComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private lateinit var navController : NavHostController
    private val shareViewModel : ShareViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubFollowerComposeTheme {
                ProvideWindowInsets {
                    navController = rememberAnimatedNavController()
                    val systemUiController = rememberSystemUiController()
                    systemUiController.setStatusBarColor(
                        color = MaterialTheme.colors.Background.copy(alpha = 0.9f),
                        darkIcons = !isSystemInDarkTheme()
                    )

                    AnimatedNavHost(
                        navController = navController,
                        startDestination = SearchScreen
                    ) {
                        composable(
                            route = SearchScreen,
                            enterTransition = { fadeIn() },
                            exitTransition = {
                                if (targetState.destination.route == ListScreen) {
                                    slideOutHorizontally(
                                        animationSpec = tween(durationMillis = 700),
                                        targetOffsetX = { -it }
                                    )
                                }
                                else fadeOut()
                            },
                            popEnterTransition = {
                                if (initialState.destination.route == ListScreen) {
                                    slideInHorizontally(
                                        animationSpec = tween(durationMillis = 700),
                                        initialOffsetX = { -it }
                                    )
                                }
                                else fadeIn()
                            }
                        ) {
                            SearchScreen(
                                navController = navController,
                                shareViewModel = shareViewModel
                            )
                        }

                        composable(
                            route = ListScreen,
                            enterTransition = {
                                slideInHorizontally(
                                    animationSpec = tween(durationMillis = 700),
                                    initialOffsetX = { it }
                                )
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                    animationSpec = tween(durationMillis = 700),
                                    targetOffsetX = { it }
                                )
                            }
                        ) {
                            ListScreen(
                                shareViewModel = shareViewModel
                            )
                        }

                        composable(
                            route = FavouriteScreen,
                            enterTransition = { fadeIn() },
                            exitTransition = {
                                if (targetState.destination.route == ListScreen) {
                                    slideOutHorizontally(
                                        animationSpec = tween(durationMillis = 700),
                                        targetOffsetX = { -it }
                                    )
                                }
                                else fadeOut()
                            },
                            popEnterTransition = {
                                if (initialState.destination.route == ListScreen) {
                                    slideInHorizontally(
                                        animationSpec = tween(durationMillis = 700),
                                        initialOffsetX = { -it }
                                    )
                                }
                                else fadeIn()
                            }
                        ) {
                            FavouriteScreen(
                                navController = navController,
                                shareViewModel = shareViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}