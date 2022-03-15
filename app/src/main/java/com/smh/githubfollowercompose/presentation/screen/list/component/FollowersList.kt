package com.smh.githubfollowercompose.presentation.screen.list.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.smh.githubfollowercompose.ErrorState
import com.smh.githubfollowercompose.R
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.presentation.component.*
import com.smh.githubfollowercompose.presentation.screen.list.ListViewModel
import com.smh.githubfollowercompose.utility.items

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun FollowersList(
    followers : LazyPagingItems<FollowerEntity>,
    searchFollowers : List<FollowerEntity>,
    searchQuery : String,
    viewModel : ListViewModel,
    errorSate : ErrorState,
    onClickFollower : (FollowerEntity) -> Unit
) {
    val result = handlePagingResult(followers = followers, errorSate = errorSate)

    if (result) {
        Column {
            SearchTextField(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp)
                    .fillMaxWidth(),
                searchName = searchQuery,
                searchError = "",
                onSearchNameChange = { searchQuery -> viewModel.onSearchQueryChange(searchQuery.trim()) },
                onSearchClick = { viewModel.searchFollower() }
            )

            LazyVerticalGrid(
                modifier = Modifier.testTag("LazyVerticalGrid"),
                cells = GridCells.Adaptive(minSize = 120.dp),
            ) {
                if (searchQuery.isEmpty()) {
                    items(
                        items = followers,
                        key = { it.id }
                    ) { follower ->
                        follower?.let {
                            viewModel.addCacheFollower(follower = follower)
                            FollowerCard(follower = follower) { onClickFollower(follower) }
                        }
                    }
                }
                else {
                    items(searchFollowers) { follower -> FollowerCard(follower = follower) { onClickFollower(follower) } }
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    followers: LazyPagingItems<FollowerEntity>,
    errorSate : ErrorState,
): Boolean {
    followers.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                FollowersLoading()
                false
            }
            error != null -> {
                when(errorSate) {
                    ErrorState.ConnectionSlow -> {
                        FollowersRetry(message = stringResource(R.string.conSlowToFetch)) {}
                    }
                    ErrorState.NoConnection -> {
                        FollowersRetry(message = stringResource(R.string.noConnection)) { }
                    }
                    else -> { FollowersRetry { followers.retry() } }
                }
                false
            }
            followers.itemCount < 1 -> {
                ListEmpty(text = "This user doesn't have \nany follower")
                false
            }
            else -> true
        }
    }
}