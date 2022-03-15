package com.smh.githubfollowercompose.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val DarkGrey = Color(0xFF141414)
val Purple = Color(0xFFBB86FC)

val Colors.Background : Color
    @Composable
    get() = if (isLight) Color.White else DarkGrey

val Colors.Accent : Color
    @Composable
    get() = if (isLight) Color.Black else Color.White

val Colors.BottomNav : Color
    @Composable
    get() = if (isLight) Color.Black else Color.Green
