package com.smh.githubfollowercompose.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smh.githubfollowercompose.R

@Composable
fun ListEmpty(
    text : String
) {
    val state = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        state.value = true
    }

    val alphaAnim by animateFloatAsState(
        targetValue = if (state.value) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .offset(y = (-130).dp)
                .align(Alignment.Center)
                .alpha(alpha = alphaAnim),
            text = text,
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Icon(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 130.dp, y = 90.dp)
                .alpha(alpha = alphaAnim),
            painter = painterResource(id = R.drawable.empty_follower),
            contentDescription = "Empty Follower",
            tint = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListEmptyPreview() {
    ListEmpty(text = "This user doesn't have \nany follower")
}