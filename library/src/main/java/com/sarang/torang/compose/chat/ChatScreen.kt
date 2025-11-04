package com.sarang.torang.compose.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.data.Chat
import com.sarang.torang.data.ChatUser


@Composable
fun ChatScreen(
    roomId: Int,
    viewModel: ChatViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    LaunchedEffect(key1 = roomId) {
        if (roomId != -1)
            viewModel.loadUserByRoomId(roomId)
    }
    val uiState = viewModel.uiState
    var show by remember { mutableStateOf(false) }

    LocalGalleryBottomSheetScaffold.current.invoke(
        GalleryBottomSheetScaffoldData(
        show = show,
        onHidden = { show = false },
        onSend = {
            viewModel.sendImages(it)
            show = false
        },
        sheetContents = { LocalGallery.current.invoke() },
        content = {
            ChatScreen(
                uiState = uiState,
                onBack = onBack,
                onValueChange = { viewModel.onMessageChange(it) },
                onSend = { viewModel.onSend() },
                onPicture = {
                    show = true
                }
            )
        }
    )
    )

}




@Composable
private fun ChatScreen(
    uiState: ChatUiState,
    onBack: () -> Unit,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onPicture: () -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 16.dp, left = 8.dp, right = 8.dp),
        topBar = {
            if (uiState is ChatUiState.Success)
                ChatScreenTopBar(onBack = onBack, uiState = uiState)
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
        roomId = 0),
        onBack = {},
        onValueChange = { message = it },
        onSend = {
            //list = list + message
            message = ""
        },
        onPicture = {

        })
}

