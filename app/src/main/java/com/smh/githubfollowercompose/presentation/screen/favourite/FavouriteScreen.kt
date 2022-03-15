package com.smh.githubfollowercompose.presentation.screen.favourite

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.smh.githubfollowercompose.R
import com.smh.githubfollowercompose.ShareViewModel
import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.presentation.component.BottomNavigationBar
import com.smh.githubfollowercompose.presentation.component.ListEmpty
import com.smh.githubfollowercompose.presentation.screen.Screen
import com.smh.githubfollowercompose.ui.theme.Accent
import com.smh.githubfollowercompose.ui.theme.Background
import com.smh.githubfollowercompose.utility.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun FavouriteScreen(
    navController: NavController,
    shareViewModel: ShareViewModel,
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(key1 = true) {
        lifecycle.addObserver(viewModel)
        onDispose { lifecycle.removeObserver(viewModel) }
    }

    val favourites = viewModel.favourites.collectAsState()
    val deleteEntity by viewModel.deleteEntity.collectAsState()

    DisplaySnackBar(
        deleteEntity = deleteEntity,
        scaffoldState = scaffoldState,
        viewModel = viewModel
    )

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        FavouriteMainComponent(
            paddingValues = it,
            favourites = favourites.value,
            goToListScreen = { name ->
                shareViewModel.changeUsername(name)
                navController.navigate(Screen.ListScreen)
            },
            deleteFavorite = { favouriteEntity ->
                viewModel.deleteFavorite(favouriteEntity)
            }
        )
    }
}


@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FavouriteMainComponent(
    paddingValues: PaddingValues,
    favourites: List<FavouriteEntity>,
    goToListScreen : (String) -> Unit,
    deleteFavorite : (FavouriteEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(MaterialTheme.colors.Background.copy(alpha = 0.9f))
            .padding(vertical = 8.dp),
    ) {
        FavouriteTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(R.string.favourites)
        )

        if (favourites.isNotEmpty()) Divider()

        LazyColumn {
            items(
                items = favourites,
                key = { it.date }
            ) { favourite ->
                val dismissState = rememberDismissState()
                val dismissDirection = dismissState.dismissDirection
                val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
                if(isDismissed && dismissDirection == DismissDirection.EndToStart) {
                    val scope = rememberCoroutineScope()
                    scope.launch {
                        delay(100)
                        deleteFavorite(favourite)
                    }
                }
                val degrees by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f)

                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
                    background = { RedBackground(degrees = degrees) },
                    dismissContent = {
                        FavouriteRow(
                            favourite = favourite,
                            goToListScreen = { goToListScreen(it) }
                        )
                    }
                )
                Divider()
            }
        }

        if (favourites.isEmpty()) ListEmpty(text = stringResource(R.string.noFavourite))
    }
}

@Composable
fun FavouriteTitle(modifier : Modifier = Modifier,title : String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        text = title,
        fontSize = MaterialTheme.typography.h4.fontSize,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colors.Accent,
        maxLines = 1,
        softWrap = true
    )
}

@ExperimentalCoilApi
@Composable
fun FavouriteRow(
    favourite : FavouriteEntity,
    goToListScreen: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .noRippleClickable { goToListScreen(favourite.login) }
            .background(MaterialTheme.colors.background)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = rememberImagePainter(
                data = favourite.imageUrl,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_github_logo)
                    transformations(RoundedCornersTransformation(topLeft = 12f,topRight = 12f,bottomLeft = 12f,bottomRight = 12f))
                    memoryCacheKey(favourite.imageUrl)
                    memoryCachePolicy(CachePolicy.ENABLED)
                }
            ),
            contentDescription = stringResource(R.string.favouriteImage),
            modifier = Modifier
                .size(60.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = favourite.name,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.Accent,
            fontSize = MaterialTheme.typography.h6.fontSize,
            maxLines = 1,
            softWrap = true
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            modifier = Modifier.padding(start = 8.dp),
            imageVector = Icons.Default.ChevronRight,
            contentDescription = stringResource(R.string.forwardArrow),
            tint = Color.Gray
        )
    }
}

@Composable
fun RedBackground(degrees : Float) {
    Column(verticalArrangement = Arrangement.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.deleteFavourite),
                tint = Color.White,
                modifier = Modifier.rotate(degrees = degrees)
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun FavouriteMainComponentPreview() {
    FavouriteMainComponent(
        paddingValues = PaddingValues(0.dp),
        favourites = emptyList(),
        goToListScreen = {},
        deleteFavorite = {}
    )
}