package com.smh.githubfollowercompose.presentation.screen.list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.smh.githubfollowercompose.R
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.ui.theme.Accent
import com.smh.githubfollowercompose.utility.noRippleClickable

@ExperimentalCoilApi
@Composable
fun FollowerCard(
    follower: FollowerEntity,
    onClick : (FollowerEntity) -> Unit
) {
    val painter = rememberImagePainter(data = follower.imageUrl) {
        placeholder(R.drawable.ic_github_logo)
        transformations(RoundedCornersTransformation(topLeft = 12f, topRight = 12f, bottomLeft = 12f, bottomRight = 12f))
        scale(Scale.FILL)
    }

    Column(
        modifier = Modifier
            .noRippleClickable { onClick(follower) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colors.onBackground
        ) {
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.followerImage),
                modifier = Modifier.size(100.dp),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = follower.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.Accent
        )
    }
}