package com.smh.githubfollowercompose.presentation.screen.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.smh.githubfollowercompose.ShareViewModel
import com.smh.githubfollowercompose.presentation.screen.list.component.ListTitle
import com.smh.githubfollowercompose.presentation.screen.list.component.FollowersList
import com.smh.githubfollowercompose.presentation.screen.list.component.ListSheetInitialContent
import com.smh.githubfollowercompose.ui.theme.Background
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    shareViewModel: ShareViewModel,
    viewModel: ListViewModel = hiltViewModel()
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    val coroutineScope = rememberCoroutineScope()

    val followers = shareViewModel.followers.collectAsLazyPagingItems()
    val name by shareViewModel.username.collectAsState()
    val errorState by shareViewModel.errorState.collectAsState()
    val searchFollowers by viewModel.searchFollower.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isFavourite by shareViewModel.isFavourite.collectAsState()
    val followerDetail by viewModel.followerDetail.collectAsState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp),
        sheetContent = {
            ListSheetInitialContent(followerDetailState = followerDetail){ name ->
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                    name?.let {
                        viewModel.clearCacheFollower()
                        shareViewModel.changeUsername(it)
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.Background.copy(alpha = 0.9f))
                    .padding(12.dp)
            ) {
                ListTitle(title = name, isFavourite = isFavourite) {
                    viewModel.addOnFavourite(name = name, isFavourite = isFavourite)
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
            
            FollowersList(
                followers = followers,
                searchFollowers = searchFollowers,
                searchQuery = searchQuery,
                viewModel = viewModel,
                errorSate = errorState,
                onClickFollower = {
                    coroutineScope.launch {
                        viewModel.getFollowerDetail(it.name)
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }
            )
        }
    }
}


