package com.sarang.torang.compose.chatroom

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.isTraceInProgress
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.R
import com.sarang.torang.data.ChatUser
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    viewmodel: ChatRoomViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onSearch: () -> Unit,
    onChat: (Int) -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
    pullToRefreshLayout: @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
    onRefresh: () -> Unit,
) {
    val coroutine = rememberCoroutineScope()
    val uiState = viewmodel.uiState
    ChatScreen(
        uiState = uiState,
        nickName = viewmodel.nickName,
        onClose = onClose,
        onSearch = onSearch,
        onChat = onChat,
        image = image,
        pullToRefreshLayout = pullToRefreshLayout,
        onRefresh = {
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
    uiState: ChatUiState,
    nickName: String,
    onClose: () -> Unit,
    onSearch: () -> Unit,
    onChat: (Int) -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
    pullToRefreshLayout: @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit)? = null,
    onRefresh: () -> Unit,
    onSignIn: () -> Unit = { Log.w("__ChatScreen", "onSignIn is not implemented!") },
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

@Composable
private fun Chat(
    uiState: ChatUiState.Success,
    onSearch: () -> Unit,
    onChat: (Int) -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
) {
    Column {
        LazyColumn {
            items(3) { it ->
                if (it == 0) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clickable { }
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.onPrimary),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onSearch) {
                            Icon(
                                imageVector = Icons.Default.Search, contentDescription = ""
                            )
                        }
                        Text(text = "Search")
                    }
                } else if (it == 1) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.height(120.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(80.dp),
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = ""
                        )
                        Text(text = "Your note", fontSize = 13.sp)
                    }
                } else if (it == 2) {
                    Tabs()
                }
            }
            items(uiState.chatItems.size) {
                ChatRoomItem(uiState.chatItems[it], image = image, onClick = onChat)
            }
        }
    }
}

@Preview
@Composable
private fun Tabs() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Message")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            modifier = Modifier.weight(1f),
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Channel")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            modifier = Modifier.weight(1f),
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Request")
        }
    }
}

@Composable
fun ChatRoomItem(
    uiState: ChatRoomUiState,
    onClick: (Int) -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
) {
    ConstraintLayout(
        modifier = Modifier
            .height(70.dp)
            .clickable { onClick.invoke(uiState.id) }
            .fillMaxWidth(),
        constraintSet = ConstraintSet {
            val image = createRefFor("image")
            val camera = createRefFor("camera")
            val nickName = createRefFor("nickName")
            val seenTime = createRefFor("seenTime")
            constrain(image) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }

            constrain(camera) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }

            constrain(nickName) {
                top.linkTo(image.top)
                bottom.linkTo(seenTime.top)
                start.linkTo(image.end, 8.dp)
                end.linkTo(camera.start)
                width = Dimension.fillToConstraints
            }
            constrain(seenTime) {
                top.linkTo(nickName.bottom)
                bottom.linkTo(image.bottom)
                start.linkTo(image.end, 8.dp)
            }
        }
    ) {
        if (!uiState.isMultiple) {
            image.invoke(
                Modifier
                    .layoutId("image")
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0x11000000)),
                uiState.profileUrl,
                30.dp,
                30.dp,
                ContentScale.Crop
            )
        } else {
            Box(modifier = Modifier.layoutId("image")) {
                image.invoke(
                    Modifier
                        .layoutId("image")
                        .size(50.dp)
                        .padding(end = 8.dp, bottom = 8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEEEEEE)),
                    uiState.profileUrl,
                    30.dp,
                    30.dp,
                    ContentScale.Crop
                )
                image.invoke(
                    Modifier
                        .layoutId("image")
                        .size(50.dp)
                        .padding(start = 8.dp, top = 5.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEEEEEE)),
                    uiState.list[1].profileUrl,
                    30.dp,
                    30.dp,
                    ContentScale.Crop
                )
            }
        }

        Text(
            text = uiState.nickName,
            modifier = Modifier.layoutId("nickName"),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = uiState.seenTime, modifier = Modifier.layoutId("seenTime"))
        IconButton(modifier = Modifier.layoutId("camera"),
            onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier.size(25.dp),
                painter = painterResource(id = R.drawable.camera),
                contentDescription = ""
            )
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

@Preview(showBackground = true)
@Composable
fun ChatRoomItemPreview() {
    ChatRoomItem(
        uiState = ChatRoomUiState(
            0,
            "Torang",
            listOf(
                ChatUser(nickName = "amy", profileUrl = "1", id = "id"),
                ChatUser(nickName = "jhone", profileUrl = "1", id = "id"),
                ChatUser(nickName = "frank", profileUrl = "1", id = "id")
            )
        ),
        onClick = {},
        image = { modifier, _, _, _, _ ->
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.gal),
                contentDescription = ""
            )
        }
    )
}