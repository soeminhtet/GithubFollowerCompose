package com.smh.githubfollowercompose.presentation.screen.list.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smh.githubfollowercompose.ui.theme.Accent

@Composable
fun FollowersLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.Accent)
    }
}

@Preview(showBackground = true)
@Composable
fun FollowersLoadingPreview() {
    FollowersLoading()
}