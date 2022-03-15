package com.smh.githubfollowercompose.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.smh.githubfollowercompose.MainActivity
import com.smh.githubfollowercompose.ShareViewModel
import com.smh.githubfollowercompose.data.local.FavouritesDao
import com.smh.githubfollowercompose.data.local.GithubDatabase
import com.smh.githubfollowercompose.data.repository.FakeAndroidRepositoryImpl
import com.smh.githubfollowercompose.di.DatabaseModule
import com.smh.githubfollowercompose.domain.usecase.*
import com.smh.githubfollowercompose.presentation.screen.Screen
import com.smh.githubfollowercompose.presentation.screen.favourite.FavouriteScreen
import com.smh.githubfollowercompose.presentation.screen.list.ListScreen
import com.smh.githubfollowercompose.presentation.screen.search.SearchScreen
import com.smh.githubfollowercompose.ui.theme.GitHubFollowerComposeTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
@RunWith(AndroidJUnit4::class)
class EndToEndTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var database : GithubDatabase

    @Inject
    lateinit var favouriteDao : FavouritesDao

    @Before
    fun setUp() {
        hiltRule.inject()
        val repository = FakeAndroidRepositoryImpl(favouritesDao = favouriteDao)
        val useCases = UseCases(
            getFollowersUseCase = GetFollowersUseCase(repository = repository),
            getFollowerDetailUseCase = GetFollowerDetailUseCase(repository = repository),
            getFavouritesUseCase = GetFavouritesUseCase(repository = repository),
            insertFavouriteUseCase = InsertFavouriteUseCase(repository = repository),
            deleteFavouriteUseCase = DeleteFavouriteUseCase(repository = repository),
            checkAlreadyFollowed = CheckAlreadyFollowed(repository = repository)
        )
        val shareViewModel = ShareViewModel(useCases)
        composeTestRule.setContent {
            val navController = rememberAnimatedNavController()
            GitHubFollowerComposeTheme {
                ProvideWindowInsets {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.SearchScreen
                    ) {
                        composable(
                            route = Screen.SearchScreen,
                            enterTransition = { fadeIn() },
                            exitTransition = {
                                if (targetState.destination.route == Screen.ListScreen) {
                                    slideOutHorizontally(
                                        animationSpec = tween(durationMillis = 700),
                                        targetOffsetX = { -it }
                                    )
                                }
                                else fadeOut()
                            },
                            popEnterTransition = {
                                if (initialState.destination.route == Screen.ListScreen) {
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
                            route = Screen.ListScreen,
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
                            route = Screen.FavouriteScreen,
                            enterTransition = { fadeIn() },
                            exitTransition = {
                                if (targetState.destination.route == Screen.ListScreen) {
                                    slideOutHorizontally(
                                        animationSpec = tween(durationMillis = 700),
                                        targetOffsetX = { -it }
                                    )
                                }
                                else fadeOut()
                            },
                            popEnterTransition = {
                                if (initialState.destination.route == Screen.ListScreen) {
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

    @After
    fun tearDown() {
        database.clearAllTables()
    }

    @Test
    fun openSearchWidget_addInputText_assertInputText() {
        composeTestRule.onNodeWithContentDescription("SearchTextFiled").performTextInput("soeminhtet")
        composeTestRule.onNodeWithContentDescription("SearchTextFiled").assertTextEquals("soeminhtet")
        composeTestRule.onNodeWithTag("SearchBtn").performClick()
        composeTestRule.onNodeWithTag("ListTitle").assertIsDisplayed()
        composeTestRule.onNodeWithTag("AddFavourite").assertIsDisplayed()
        composeTestRule.onNodeWithTag("AddFavourite").performClick()
        composeTestRule.onNodeWithTag("LazyVerticalGrid").onChildren().assertCountEquals(1)
        composeTestRule.onNodeWithTag("LazyVerticalGrid").onChildren().onFirst().assert(hasText("SAllen0400"))
        composeTestRule.onNodeWithTag("LazyVerticalGrid").onChildren().onFirst().performClick()
        composeTestRule.onNodeWithTag("SheetLoading").assertIsDisplayed()
    }
}