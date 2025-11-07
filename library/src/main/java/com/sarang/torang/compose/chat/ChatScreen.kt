package com.sarang.torang.compose.chat

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
    tag         : String        = "__ChatScreen",
    roomId      : Int           = -1,
    viewModel   : ChatViewModel = hiltViewModel(),
    onBack      : () -> Unit    = {},
) {
    val uiState : ChatUiState   = viewModel.uiState
    var show    : Boolean       by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = roomId) {
        if (roomId == -1){
            Log.e(tag, "do noting. roomId is -1")
        }else{
            viewModel.loadUserByRoomId(roomId)
        }
    }

    val chatScreen : @Composable ()->Unit = {
        ChatScreen(
            uiState         = uiState,
            onBack          = onBack,
            onValueChange   = { viewModel.onMessageChange(it) },
            onSend          = { viewModel.onSend() },
            onPicture       = { show = true }
        )
    }

    LocalGalleryBottomSheetScaffold.current.invoke( // 갤러리 외부에서 설정
        GalleryBottomSheetScaffoldData(
            show            = show,
            onHidden        = { show = false },
            onSend          = { viewModel.sendImages(it)
                                show = false },
            sheetContents   = { LocalGallery.current.invoke() },
            content         = { chatScreen() }
        )
    )
}


@Composable
private fun ChatScreen(
    uiState         : ChatUiState       = ChatUiState.Loading,
    onBack          : () -> Unit        = {},
    onValueChange   : (String) -> Unit  = {},
    onSend          : () -> Unit        = {},
    onPicture       : () -> Unit        = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState){
            is ChatUiState.Error -> {
                Error(uiState = uiState)
            }
            ChatUiState.Loading -> {
                Loading()
            }
            is ChatUiState.Success -> {
                Success(
                    uiState = uiState,
                    onBack  = onBack,
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
private fun Error(uiState: ChatUiState.Error = ChatUiState.Error("")){
    Box(Modifier.fillMaxSize()){
        Text(modifier = Modifier.align(Alignment.Center),text = uiState.message)
    }
}
@Preview
@Composable
private fun Loading(){
    Box(Modifier.fillMaxSize()){
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun Success(
    uiState         : ChatUiState.Success = ChatUiState.Success(),
    onBack          : () -> Unit        = {},
    onValueChange   : (String) -> Unit  = {},
    onSend          : () -> Unit        = {},
    onPicture       : () -> Unit        = {},
){
    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 16.dp, left = 8.dp, right = 8.dp),
        topBar = {
            ChatScreenTopBar(onBack = onBack, uiState = uiState)
        }
    ) {
        Box(Modifier.padding(it)) {
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
        onValueChange = { message = it },
        )
}

