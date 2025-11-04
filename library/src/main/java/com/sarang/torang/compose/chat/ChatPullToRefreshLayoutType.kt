package com.sarang.torang.compose.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

typealias ChatPullToRefreshLayoutType = @Composable (ChatPullToRefreshLayoutData) -> Unit

data class ChatPullToRefreshLayoutData(
    val isRefreshing: Boolean = false,
    val onRefresh: () -> Unit = {},
    val contents: @Composable () -> Unit = {}
)

val LocalChatPullToRefreshLayout: ProvidableCompositionLocal<ChatPullToRefreshLayoutType> =
    compositionLocalOf {
        @Composable {
            it.contents.invoke()
        }
    }
