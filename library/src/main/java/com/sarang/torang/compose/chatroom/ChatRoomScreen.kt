package com.sarang.torang.compose.chatroom

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sarang.torang.compose.chat.ChatPullToRefreshLayoutData
import com.sarang.torang.compose.chat.ChatTopAppBar
import com.sarang.torang.compose.chat.LocalChatPullToRefreshLayout
import com.sarang.torang.data.ChatUser
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    var deleteId by remember { mutableIntStateOf(-1) }
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
        },
        onDelete            = {
            deleteId = it
        }
    )

    if(deleteId > 0){
        DeleteDialog(
            onDismissRequest = {
                deleteId = -1
            },
            onDelete = {
                coroutine.launch {
                    viewmodel.deleteRoom(deleteId)
                    deleteId = -1
                }
            }
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DeleteDialog(onDelete: () -> Unit = {}, onDismissRequest: () -> Unit = {}) {
    BasicAlertDialog(onDismissRequest = onDismissRequest){
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ){
            Column(modifier = Modifier.padding(16.dp)) {
                Text("채팅방을 삭제 하시겠습니까?")
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.width(200.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onDelete) {
                        Text("예")

                    }
                    Button(onDismissRequest) {
                        Text("아니오")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatRoomScreen(
    uiState    : ChatUiState    = ChatUiState.Loading,
    nickName   : String         = "",
    onClose    : () -> Unit     = {},
    onSearch   : () -> Unit     = {},
    onChat     : (Int) -> Unit  = {},
    onRefresh  : () -> Unit     = {},
    onSignIn   : () -> Unit     = { Log.w("__ChatScreen", "onSignIn is not implemented!") },
    onDelete   : (Int) -> Unit  = {},
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
                        onChat = onChat,
                        onDelete = onDelete
                    )
                }

                is ChatUiState.Error -> {
                    Text(uiState.message)
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
    uiState     : ChatUiState.Success   = ChatUiState.Success(),
    onRefresh   : () -> Unit            = {},
    onSearch    : () -> Unit            = {},
    onChat      : (Int) -> Unit         = {},
    onDelete    : (Int) -> Unit         = {},
){
    var showModalWithRoomId by remember { mutableIntStateOf(0) }
    LocalChatPullToRefreshLayout.current.invoke(
        ChatPullToRefreshLayoutData(
            isRefreshing = false,
            onRefresh = { onRefresh.invoke() },
            contents = {
                ChatList(
                    uiState = uiState,
                    onSearch = onSearch,
                    onChat = onChat,
                    onLongClick = { showModalWithRoomId = it }
                )
            }
        )
    )

    if(showModalWithRoomId > 0){
        ModalBottomSheet(
            onDismissRequest = { showModalWithRoomId = 0 }
        ) {
            BottomMenu(
                uiState.chatItems.firstOrNull {
                    it.id == showModalWithRoomId
                }?.nickName ?: "",
                onDelete                = {
                                            onDelete(showModalWithRoomId)
                                            showModalWithRoomId = 0
                                          },
                onFix                   = { showModalWithRoomId = 0 },
                onTurnOffCallAlarm      = { showModalWithRoomId = 0 },
                onTurnOffMessageAlarm   = { showModalWithRoomId = 0 }
            )
        }
    }
}

@Preview
@Composable
private fun BottomMenu(
    nickName                : String        = "",
    onFix                   : () -> Unit    = {},
    onDelete                : () -> Unit    = {},
    onTurnOffMessageAlarm   : () -> Unit    = {},
    onTurnOffCallAlarm      : () -> Unit    = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = nickName)
        }
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clickable(onClick = onFix),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "고정"
            )
        }
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clickable(onClick = onDelete),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "삭제"
            )
        }
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clickable(onClick = onTurnOffMessageAlarm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "메시지 알림 해제"
            )
        }
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clickable(onClick = onTurnOffCallAlarm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "통화 알림 해제"
            )
        }
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
fun ChatScreenSuccessPreview() {
    var uiState by remember { mutableStateOf(ChatUiState.Success(/*Preview*/
        chatItems = listOf(
            ChatRoomUiState(
                1,
                "10min",
                listOf(
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                )
            ),
            ChatRoomUiState(
                2,
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
                3,
                "20min",
                listOf(
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                )
            ),
            ChatRoomUiState(
                3,
                "26min",
                listOf(ChatUser(nickName = "nickName", profileUrl = "1", id = "id"))
            ),
        )
    )) }
    ChatRoomScreen(uiState = uiState,
        nickName = "nickName",
        onClose = {},
        onChat = {},
        onSearch = {},
        onRefresh = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ChatScreenLoadingPreview() {
    ChatRoomScreen(uiState = ChatUiState.Loading)
}

@Preview
@Composable
fun ChatScreenEmptyPreview(){
    ChatRoomScreen(
        uiState = ChatUiState.Success()
    ) {  }
}