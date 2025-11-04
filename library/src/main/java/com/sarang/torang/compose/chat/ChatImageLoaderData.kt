package com.sarang.torang.compose.chat

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ChatImageLoaderData(
    val modifier        : Modifier      = Modifier,
    val url             : String        = "",
    val iconSize        : Dp?           = 0.dp,
    val progressSize    : Dp?           = 0.dp,
    val contentScale    : ContentScale  = ContentScale.Fit
)
