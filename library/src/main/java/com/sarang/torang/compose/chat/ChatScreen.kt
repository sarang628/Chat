package com.sarang.torang.compose.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.R
import com.sarang.torang.data.Chat
import com.sarang.torang.data.ChatUser

@Composable
fun ChatScreen(
    roomId: Int,
    viewModel: ChatViewModel = hiltViewModel(),
    onBack: () -> Unit,
    galleryCompose: @Composable () -> Unit,
    galleryBottomSheetScaffoldCompose: @Composable (
        show: Boolean,
        onHidden: () -> Unit,
        onSend: (List<String>) -> Unit,
        sheetContents: @Composable () -> Unit,
        content: @Composable (PaddingValues) -> Unit,
    ) -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
) {
    LaunchedEffect(key1 = roomId) {
        if (roomId != -1)
            viewModel.loadUserByRoomId(roomId)
    }
    val uiState = viewModel.uiState
    var show by remember { mutableStateOf(false) }

    galleryBottomSheetScaffoldCompose.invoke(
        show,
        { show = false },
        {
            viewModel.sendImages(it)
            show = false
        },
        { galleryCompose.invoke() },
        {
            ChatScreen(
                uiState = uiState,
                image = image,
                onBack = onBack,
                onValueChange = { viewModel.onMessageChange(it) },
                onSend = { viewModel.onSend() },
                onPicture = {
                    show = true
                }
            )
        }
    )

}



@Composable
private fun ChatScreen(
    uiState: ChatUiState,
    onBack: () -> Unit,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onPicture: () -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
) {
    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 16.dp, left = 8.dp, right = 8.dp),
        topBar = {
            if (uiState is ChatUiState.Success)
                ChatScreenTopBar(onBack = onBack, uiState = uiState, image = image)
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (uiState is ChatUiState.Success) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    reverseLayout = true,
                    contentPadding = PaddingValues(bottom = 65.dp)
                ) {
                    items(uiState.chats.size) {
                        ChatItemMe(
                            message = uiState.chats[it].message,
                            isMe = uiState.chats[it].isMe,
                            profileUrl = uiState.chats[it].profileUrl,
                            image = image,
                            isSending = uiState.chats[it].isSending
                        )
                    }
                }

                ChatScreenInput(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    uiState = uiState,
                    onValueChange = onValueChange,
                    onSend = onSend,
                    onPicture = onPicture
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview1(
) {
    var message by remember { mutableStateOf("") }
    var list: List<Chat> by remember { mutableStateOf(listOf()) }
    ChatScreen(uiState = ChatUiState.Success(/*Preview*/
        user = listOf(ChatUser(nickName = "nickName", id = "", profileUrl = "")),
        message = message,
        chats = list,
        roomId = 0
    ),
        image = { _, _, _, _, _ -> },
        onBack = {},
        onValueChange = { message = it },
        onSend = {
            //list = list + message
            message = ""
        },
        onPicture = {

        })
}

