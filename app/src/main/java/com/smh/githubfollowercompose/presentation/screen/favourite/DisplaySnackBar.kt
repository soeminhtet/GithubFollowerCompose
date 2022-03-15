package com.smh.githubfollowercompose.presentation.screen.favourite

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.R

@Composable
fun DisplaySnackBar(
    deleteEntity : FavouriteEntity?,
    scaffoldState : ScaffoldState,
    viewModel: FavouriteViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = deleteEntity) {
        deleteEntity?.let {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = context.getString(R.string.deleteSnackFavourite,deleteEntity.login),
                actionLabel = "undo"
            )

            if (result == SnackbarResult.ActionPerformed) {
                viewModel.undoDeleteFavourite()
            }

            if (result == SnackbarResult.Dismissed) {
                viewModel.setDeleteEntityToNull()
            }
        }
    }
}