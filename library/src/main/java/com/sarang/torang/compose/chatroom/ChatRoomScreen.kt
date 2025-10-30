package com.sarang.torang.compose.chatroom

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.R
import com.sarang.torang.data.ChatUser
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    viewmodel           : ChatRoomViewModel = hiltViewModel(),
    onClose             : () -> Unit,
    onSearch            : () -> Unit,
    onChat              : (Int) -> Unit,
    image               : @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
    pullToRefreshLayout : @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
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
        image               = image,
        pullToRefreshLayout = pullToRefreshLayout,
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
    uiState             : ChatUiState,
    nickName            : String,
    onClose             : () -> Unit,
    onSearch            : () -> Unit,
    onChat              : (Int) -> Unit,
    image               : @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
    pullToRefreshLayout : @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
    onRefresh           : () -> Unit,
    onSignIn            : () -> Unit = { Log.w("__ChatScreen", "onSignIn is not implemented!") },
) {
    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = ""
                    )
                }
            }, title = {
                Text(text = nickName)
            }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle, contentDescription = ""
                    )
                }
            })
        }, contentWindowInsets = WindowInsets(left = 12.dp, right = 12.dp)
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
                    pullToRefreshLayout?.invoke(false, {
                        onRefresh.invoke()
                    }, {
                        Chat(uiState, image = image, onSearch = onSearch, onChat = onChat)
                    })
                }

                is ChatUiState.Error -> {

                }

                is ChatUiState.Logout -> {
                    Column(
                        Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "로그인을 해주세요.")
                        Button(onClick = onSignIn) {
                            Text(text = "SIGN IN WITH EMAIL")
                        }
                    }
                }
            }
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
        onRefresh = {},
        image = { modifier, _, _, _, _ ->
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.gal),
                contentDescription = ""
            )
        },
        pullToRefreshLayout = { _, _, contents ->
            Box(modifier = Modifier.fillMaxSize()) {
                contents.invoke()
            }
        }
    )
}