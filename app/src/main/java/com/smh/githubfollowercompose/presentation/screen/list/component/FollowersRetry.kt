package com.smh.githubfollowercompose.presentation.screen.list.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ReportProblem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smh.githubfollowercompose.R
import com.smh.githubfollowercompose.utility.noRippleClickable

@Composable
fun FollowersRetry(
    message : String? = null,
    onclick : () -> Unit
) {
    Box(
        modifier = Modifier
            .noRippleClickable { onclick() }
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                imageVector = Icons.Outlined.ReportProblem,
                contentDescription = stringResource(R.string.retryIcon),
                tint = Color.Gray
            )

            Text(
                text = message ?: stringResource(R.string.topToRetry),
                color = Color.Gray,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FollowersRetryPreview() {
    FollowersRetry {}
}