package com.sarang.torang.compose.chat

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.R
import com.sarang.torang.compose.chatroom.isMultiple
import com.sarang.torang.compose.chatroom.profileUrl
import com.sarang.torang.data.ChatUser

@Composable
fun ChatScreen(
    userId: Int,
    viewModel: ChatViewModel = hiltViewModel(),
    onBack: () -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
) {
    LaunchedEffect(key1 = userId) {
        viewModel.loadUser(userId)
    }
    val uiState = viewModel.uiState
    ChatScreen(uiState = uiState,
        image = image,
        onBack = onBack,
        onValueChange = { viewModel.onMessageChange(it) },
        onSend = { viewModel.onSend() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(
    uiState: ChatUiState,
    onBack: () -> Unit,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
) {
    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 16.dp, left = 8.dp, right = 8.dp),
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = ""
                    )
                }
            }, title = {
                Row(Modifier.height(50.dp)) {
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
                                uiState.user[1].profileUrl,
                                30.dp,
                                30.dp,
                                ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = uiState.nickName, fontSize = 14.sp, style = TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = uiState.id, fontSize = 14.sp, style = TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                    }
                }
            }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.call),
                        contentDescription = ""
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.video),
                        contentDescription = ""
                    )
                }
            })
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                reverseLayout = true,
                contentPadding = PaddingValues(bottom = 65.dp)
            ) {
                items(uiState.chats.size) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 24.dp,
                                        bottomStart = 24.dp,
                                        topEnd = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                )
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp)
                        ) {
                            Text(
                                text = "${uiState.chats[it]}",
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            TextField(value = uiState.message,
                onValueChange = onValueChange,
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                leadingIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 5.dp),
                        onClick = { },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            tint = Color.White,
                            modifier = Modifier.size(20.dp),
                            painter = if (!uiState.canSend) painterResource(id = R.drawable.camera1)
                            else painterResource(id = R.drawable.search),
                            contentDescription = ""
                        )
                    }
                },
                trailingIcon = {
                    Row(Modifier.padding(end = 8.dp)) {
                        if (!uiState.canSend) {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.microphone),
                                    contentDescription = ""
                                )
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.gal),
                                    contentDescription = ""
                                )
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.emo),
                                    contentDescription = ""
                                )
                            }
                        } else {
                            TextButton(onClick = { if (uiState.canSend) onSend.invoke() }) {
                                Text(text = "Send")
                            }
                        }
                    }
                },
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,  // 포커스 시 하단 선 제거
                    unfocusedIndicatorColor = Color.Transparent  // 비포커스 시 하단 선 제거
                ),
                placeholder = {
                    Text(text = "Message...")
                })
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview1(
) {
    var message by remember { mutableStateOf("") }
    var list: List<String> by remember { mutableStateOf(listOf()) }
    ChatScreen(uiState = ChatUiState(
        user = listOf(ChatUser(nickName = "nickName", id = "", profileUrl = "")),
        message = message,
        chats = list
    ),
        image = { _, _, _, _, _ -> },
        onBack = {},
        onValueChange = { message = it },
        onSend = {
            list = list + message
            message = ""
        })
}