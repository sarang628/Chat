package com.sarang.torang.compose.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

typealias ChatImageLoaderType = @Composable (ChatImageLoaderData) -> Unit

val LocalChatImageLoader : ProvidableCompositionLocal<ChatImageLoaderType> =
    compositionLocalOf<ChatImageLoaderType> {
        @Composable {

        }
    }