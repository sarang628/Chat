package com.sarang.torang.compose.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
private fun ChatItemMe1(
    message: String,
    isMe: Boolean,
    profileUrl: String,
    isSending: Boolean
) {
    val density = LocalDensity.current

    val state = remember {
        MutableTransitionState(isSending).apply {
            // Start the animation immediately.
            targetState = isSending
        }
    }

    if (isMe) {
        AnimatedVisibility(
            visibleState = state,
            enter = EnterTransition.None,
            exit = slideOutHorizontally { with(density) { 20.dp.roundToPx() } }
        ) {
            RightChatItem(
                message = message,
                isSending = true
            )
        }
        if (!isSending && !(state.isIdle && state.currentState)) {
            RightChatItem(
                message = message,
                isSending = false
            )
        }
    } else {
        LeftChatItem(
            message = message,
            profileUrl = profileUrl
        )
    }
}