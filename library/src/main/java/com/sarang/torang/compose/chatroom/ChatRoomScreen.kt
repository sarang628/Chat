package com.sarang.torang.compose.chatroom

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.chat.ChatPullToRefreshLayoutData
import com.sarang.torang.compose.chat.ChatTopAppBar
import com.sarang.torang.compose.chat.LocalChatPullToRefreshLayout
import com.sarang.torang.data.ChatUser
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    viewmodel           : ChatRoomViewModel = hiltViewModel(),
    onClose             : () -> Unit,
    onSearch            : () -> Unit,
    onChat              : (Int) -> Unit,
    onRefresh           : () -> Unit,
) {
    val coroutine = rememberCoroutineScope()
    val uiState = viewmodel.uiState
    ChatScreen(
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
@Composable
private fun ChatScreen(
    uiState    : ChatUiState,
    nickName   : String,
    onClose    : () -> Unit,
    onSearch   : () -> Unit,
    onChat     : (Int) -> Unit,
    onRefresh  : () -> Unit,
    onSignIn   : () -> Unit = { Log.w("__ChatScreen", "onSignIn is not implemented!") },
) {
    Scaffold(
        contentWindowInsets = WindowInsets(left = 12.dp, right = 12.dp),
        topBar = {
            ChatTopAppBar(
                nickName = nickName,
                onClose = onClose
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (uiState) {
                is ChatUiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                is ChatUiState.Success -> {
                    uiState.Render(
                        onRefresh = onRefresh,
                        onSearch = onSearch,
                        onChat = onChat
                    )
                }

                is ChatUiState.Error -> {

                }

                is ChatUiState.Logout -> {
                    uiState.Render(
                        modifier = Modifier.align(Alignment.Center),
                        onSignIn = onSignIn
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatUiState.Success.Render(
    onRefresh: () -> Unit = {},
    onSearch: () -> Unit = {},
    onChat: (Int) -> Unit = {}
){
    LocalChatPullToRefreshLayout.current.invoke(
        ChatPullToRefreshLayoutData(
            isRefreshing = false,
            onRefresh = { onRefresh.invoke() },
            contents = {
                ChatList(
                    uiState = this,
                    onSearch = onSearch,
                    onChat = onChat
                )
            }
        )
    )
}

@Composable
fun ChatUiState.Logout.Render(
    modifier : Modifier,
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
    ChatScreen(uiState = ChatUiState.Success(
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