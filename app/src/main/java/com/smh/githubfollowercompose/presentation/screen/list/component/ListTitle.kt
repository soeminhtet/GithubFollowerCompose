package com.smh.githubfollowercompose.presentation.screen.list.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smh.githubfollowercompose.R
import com.smh.githubfollowercompose.ui.theme.Accent
import com.smh.githubfollowercompose.utility.noRippleClickable

@Composable
fun ListTitle(
    title : String,
    isFavourite : Boolean,
    onFavouriteClick : () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 12.dp)
                .testTag("ListTitle"),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.Accent,
            maxLines = 1,
            softWrap = true
        )

        Crossfade(targetState = isFavourite) {
            if (it) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.addFavourite),
                    tint = MaterialTheme.colors.error,
                    modifier = Modifier
                        .noRippleClickable { onFavouriteClick() }
                        .padding(horizontal = 8.dp)
                        .size(24.dp)
                        .testTag("Favourite"),
                )
            }
            else {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.addFavourite),
                    tint = MaterialTheme.colors.Accent,
                    modifier = Modifier
                        .noRippleClickable { onFavouriteClick() }
                        .padding(horizontal = 8.dp)
                        .size(24.dp)
                        .testTag("AddFavourite")
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenTitlePreview() {
    ListTitle(title = "SoeMinHtet",isFavourite = true){}
}