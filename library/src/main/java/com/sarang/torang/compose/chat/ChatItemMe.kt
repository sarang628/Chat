package com.sarang.torang.compose.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun ChatItemMe(
    message: String,
    isMe: Boolean,
    profileUrl: String,
    isSending: Boolean
) {
    val density = LocalDensity.current

    // Update the transition state whenever isSending changes
    val state = remember { MutableTransitionState(isSending) }

    LaunchedEffect(isSending) {
        // This ensures that the transition state updates when isSending changes
        state.targetState = isSending
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

        if (!isSending && !state.currentState) {
            RightChatItem(
                message = message,
                isSending = false
            )
        }
    } else {
        LeftChatItem(
            message = message,
            profileUrl = profileUrl,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatItem1() {
    ChatItemMe(/*Preview*/
        message = "aaa", isMe = true, profileUrl = "", isSending = true
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewChatItem() {
    ChatItemMe(/*Preview*/
        message = "aaa",
        isMe = false,
        profileUrl = "",
        isSending = true
    )
}