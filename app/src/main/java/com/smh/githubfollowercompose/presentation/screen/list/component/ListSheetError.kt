package com.smh.githubfollowercompose.presentation.screen.list.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material.icons.outlined.ReportProblem
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smh.githubfollowercompose.R
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun ListSheetError(
    status : Int?,
    message : String?,
    exception: Exception?
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (status != null && message != null) {
                Icon(
                    modifier = Modifier.size(100.dp),
                    imageVector = if (status == 403) Icons.Outlined.Report else Icons.Outlined.ReportProblem,
                    contentDescription = stringResource(R.string.failIcon),
                    tint = Color.Gray
                )

                Text(
                    text = if (status == 403) "Api call limitation reach\nTry later" else message,
                    color = Color.Gray,
                )
            } else {
                exception?.let {
                    when(it) {
                        is SocketTimeoutException -> {
                            Icon(
                                modifier = Modifier.size(100.dp),
                                imageVector = Icons.Outlined.SentimentDissatisfied,
                                contentDescription = stringResource(R.string.connectionSlowIcon),
                                tint = Color.Gray
                            )

                            Text(
                                text = stringResource(R.string.conSlowToFetch),
                                color = Color.Gray,
                            )
                        }
                        is ConnectException -> {
                            Icon(
                                modifier = Modifier.size(100.dp),
                                imageVector = Icons.Outlined.SentimentDissatisfied,
                                contentDescription = stringResource(id = R.string.connectionSlowIcon),
                                tint = Color.Gray
                            )

                            Text(
                                text = stringResource(R.string.noConnection),
                                color = Color.Gray,
                            )
                        }
                     }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListSheetErrorPreview() {
    ListSheetError(
        status = 403,
        message = null,
        exception  = null
    )
}