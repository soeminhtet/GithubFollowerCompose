package com.smh.githubfollowercompose.presentation.screen.list.component

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.smh.githubfollowercompose.ui.theme.Accent
import com.smh.githubfollowercompose.utility.noRippleClickable
import com.smh.githubfollowercompose.R
import com.smh.githubfollowercompose.domain.model.remote.FollowerDetailApiModel
import com.smh.githubfollowercompose.domain.model.remote.dummyDetail
import com.smh.githubfollowercompose.presentation.screen.list.FollowerDetailState
import com.smh.githubfollowercompose.ui.theme.Purple
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoilApi
@Composable
fun ListSheetInitialContent(
    followerDetailState : FollowerDetailState,
    onClickDone : (String?) -> Unit
) {
    when(followerDetailState) {
        FollowerDetailState.Loading -> {
            Column(modifier = Modifier.padding(12.dp).testTag("SheetLoading")) {
                ListHeader { onClickDone(it) }
                FollowersLoading()
            }
        }
        is FollowerDetailState.Fail -> {
            Column(modifier = Modifier.padding(12.dp)) {
                ListHeader { onClickDone(it) }
                ListSheetError(
                    status = followerDetailState.status,
                    message = followerDetailState.message,
                    exception = followerDetailState.exception)
            }
        }
        is FollowerDetailState.Success -> ListSheetContent(followerDetail = followerDetailState.data,onClickDone = onClickDone)
    }
}

@ExperimentalCoilApi
@SuppressLint("SimpleDateFormat")
@Composable
fun ListSheetContent(
    followerDetail : FollowerDetailApiModel,
    onClickDone : (String?) -> Unit
) {
    val context = LocalContext.current
    val inputDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val outputDate = SimpleDateFormat("MMM yyyy")
    var date : Date = Calendar.getInstance().time
    try {
        inputDate.timeZone = TimeZone.getTimeZone("UTC")
        date = inputDate.parse(followerDetail.createdAt) ?: Calendar.getInstance().time
    }catch (e : ParseException) {
        Log.e("LoginSheet",e.localizedMessage ?: "Date Parse error")
    }
    val formattedDate = outputDate.format(date)

    Column(
        modifier = Modifier.padding(12.dp),
    ) {
        ListHeader { onClickDone(it) }

        Row {
            Image(
                painter = rememberImagePainter(
                    data = followerDetail.avatarUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_github_logo)
                        transformations(RoundedCornersTransformation(topLeft = 12f,topRight = 12f,bottomLeft = 12f,bottomRight = 12f))
                        memoryCacheKey(followerDetail.avatarUrl)
                        memoryCachePolicy(CachePolicy.ENABLED)
                    }
                ),
                contentDescription = "Follower Image",
                modifier = Modifier
                    .size(100.dp)
            )

            Column(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = followerDetail.login,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    softWrap = true
                )

                Text(
                    text = followerDetail.name ?: "Anonymous",
                    fontSize = MaterialTheme.typography.body2.fontSize,
                    maxLines = 1,
                    softWrap = true,
                    color = Color.Gray
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PushPin,
                        contentDescription = "LocationIcon",
                        tint = Color.Gray
                    )

                    Text(
                        text = followerDetail.location ?: "No location",
                        fontSize = MaterialTheme.typography.body2.fontSize,
                        maxLines = 1,
                        softWrap = true,
                        color = Color.Gray
                    )
                }
            }
        }

        Text(
            modifier = Modifier.padding(vertical = 20.dp),
            text = followerDetail.bio ?: "No Bio",
            fontSize = MaterialTheme.typography.body2.fontSize,
            maxLines = 3,
            softWrap = true,
            color = MaterialTheme.colors.Accent
        )

        ListSheetCard(
            firstIcon = Icons.Outlined.Folder,
            secondIcon = Icons.Outlined.FormatAlignLeft,
            firstTitle = "Public Repos",
            secondTitle = "Public Gists",
            firstCount = followerDetail.publicRepos.toString(),
            secondCount = followerDetail.publicGists.toString(),
            buttonText = "Github Profile",
            buttonBackground = Color.Red
        ) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(followerDetail.htmlUrl)
            context.startActivity(intent)
        }

        Spacer(modifier = Modifier.height(20.dp))
        
        ListSheetCard(
            firstIcon = Icons.Outlined.FavoriteBorder,
            secondIcon = Icons.Outlined.People,
            firstTitle = "Followers",
            secondTitle = "Following",
            firstCount = followerDetail.followers.toString(),
            secondCount = followerDetail.following.toString(),
            buttonText = "Get Followers",
            buttonBackground = Purple
        ) {
            onClickDone(followerDetail.login)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Github since $formattedDate",
            fontSize = MaterialTheme.typography.caption.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            softWrap = true,
            color = MaterialTheme.colors.Accent,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ListHeader(
    onClickDone: (String?) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            modifier = Modifier
                .noRippleClickable { onClickDone(null) }
                .padding(8.dp),
            text = stringResource(R.string.done),
            color = MaterialTheme.colors.Accent,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ListSheetCard(
    firstIcon : ImageVector,
    secondIcon : ImageVector,
    firstTitle : String,
    secondTitle : String,
    firstCount : String,
    secondCount : String,
    buttonText : String,
    buttonBackground : Color,
    onClick : () -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier.padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = firstIcon,
                            contentDescription = firstTitle
                        )
                        Text(
                            modifier = Modifier.padding(start = 12.dp),
                            text = firstTitle,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            maxLines = 1,
                            softWrap = true,
                            color = MaterialTheme.colors.Accent,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        modifier = Modifier.padding(vertical = 12.dp),
                        text = firstCount,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                        maxLines = 1,
                        softWrap = true,
                        color = MaterialTheme.colors.Accent,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier.padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = secondIcon,
                            contentDescription = secondTitle
                        )
                        Text(
                            modifier = Modifier.padding(start = 12.dp),
                            text = secondTitle,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            maxLines = 1,
                            softWrap = true,
                            color = MaterialTheme.colors.Accent,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        modifier = Modifier.padding(vertical = 12.dp),
                        text = secondCount,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                        maxLines = 1,
                        softWrap = true,
                        color = MaterialTheme.colors.Accent,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                onClick = { onClick() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonBackground
                )
            ) {
                Text(
                    text = buttonText,
                    fontSize = MaterialTheme.typography.body2.fontSize,
                    maxLines = 1,
                    softWrap = true,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListSheetContentPreview() {
    ListSheetContent(
        followerDetail = dummyDetail
    ){}
}