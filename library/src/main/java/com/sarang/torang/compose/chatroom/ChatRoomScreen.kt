package com.sarang.torang.compose.chatroom

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sarang.torang.compose.chat.ChatPullToRefreshLayoutData
import com.sarang.torang.compose.chat.ChatTopAppBar
import com.sarang.torang.compose.chat.LocalChatPullToRefreshLayout
import com.sarang.torang.data.ChatUser
import kotlinx.coroutines.launch

@Composable
fun ChatRoomScreen(
    viewmodel           : ChatRoomViewModel = hiltViewModel(),
    onClose             : () -> Unit,
    onSearch            : () -> Unit,
    onChat              : (Int) -> Unit,
    onRefresh           : () -> Unit,
) {
    val uiState by viewmodel.uiState2.collectAsStateWithLifecycle()
    val coroutine = rememberCoroutineScope()
    ChatRoomScreen(
        uiState             = uiState,
        nickName            = viewmodel.nickName,
        onClose             = onClose,
        onSearch            = onSearch,
        onChat              = onChat,
        onRefresh           = {
            coroutine.launch {
                viewmodel.refresh()
                onRefresh.invoke()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ChatRoomScreen(
    uiState    : ChatUiState    = ChatUiState.Loading,
    nickName   : String         = "",
    onClose    : () -> Unit     = {},
    onSearch   : () -> Unit     = {},
    onChat     : (Int) -> Unit  = {},
    onRefresh  : () -> Unit     = {},
    onSignIn   : () -> Unit     = { Log.w("__ChatScreen", "onSignIn is not implemented!") },
) {
    Scaffold(
        topBar = {
            ChatTopAppBar(
                nickName = nickName,
                onClose = onClose
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (uiState) {
                is ChatUiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                is ChatUiState.Success -> {
                    Success(
                        uiState = uiState,
                        onRefresh = onRefresh,
                        onSearch = onSearch,
                        onChat = onChat
                    )
                }

                is ChatUiState.Error -> {

                }

                is ChatUiState.Logout -> {
                    Logout(
                        modifier = Modifier.align(Alignment.Center),
                        onSignIn = onSignIn
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun Success(
    uiState: ChatUiState.Success = ChatUiState.Success(),
    onRefresh: () -> Unit = {},
    onSearch: () -> Unit = {},
    onChat: (Int) -> Unit = {},
){
    var showModal by remember { mutableStateOf(false) }
    LocalChatPullToRefreshLayout.current.invoke(
        ChatPullToRefreshLayoutData(
            isRefreshing = false,
            onRefresh = { onRefresh.invoke() },
            contents = {
                ChatList(
                    uiState = uiState,
                    onSearch = onSearch,
                    onChat = onChat,
                    onLongClick = { showModal = true }
                )
            }
        )
    )

    if(showModal){
        ModalBottomSheet(
            onDismissRequest = { showModal = false }
        ) {

        }
    }
}

@Preview
@Composable
private fun BottomMenu(){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.height(50.dp),
            text = "sryang",
        )
        Text(
            modifier = Modifier.height(16.dp),
            text = "고정"
        )
        Text(
            modifier = Modifier.height(16.dp),
            text = "삭제"
        )
        Text(
            modifier = Modifier.height(16.dp),
            text = "메시지 알림 해제"
        )
        Text(
            modifier = Modifier.height(16.dp),
            text = "통화 알림 해제"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Logout(
    modifier : Modifier = Modifier,
    onSignIn: () -> Unit = {}
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "로그인을 해주세요.")
        Button(onClick = onSignIn) {
            Text(text = "SIGN IN WITH EMAIL")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatRoomScreen(uiState = ChatUiState.Success(
        chatItems = listOf(
            ChatRoomUiState(
                0,
                "10min",
                listOf(
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                )
            ),
            ChatRoomUiState(
                0,
                "15min",
                listOf(
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                )
            ),
            ChatRoomUiState(
                0,
                "20min",
                listOf(
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                )
            ),
            ChatRoomUiState(
                0,
                "26min",
                listOf(ChatUser(nickName = "nickName", profileUrl = "1", id = "id"))
            ),
        )
    ), nickName = "nickName",
        onClose = {},
        onChat = {},
        onSearch = {},
        onRefresh = {}
    )
}