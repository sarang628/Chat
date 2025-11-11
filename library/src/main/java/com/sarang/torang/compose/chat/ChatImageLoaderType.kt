package com.sarang.torang.compose.chat

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.res.painterResource
import com.sarang.torang.R

typealias ChatImageLoaderType = @Composable (ChatImageLoaderData) -> Unit

val LocalChatImageLoader : ProvidableCompositionLocal<ChatImageLoaderType> =
    compositionLocalOf<ChatImageLoaderType> {
        @Composable {
            Image(
                modifier = it.modifier,
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null
            )
        }
    }