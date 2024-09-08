package com.sarang.torang

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewmodel: ChatViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onSearch: () -> Unit,
    onChat: (Int) -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
) {
    val uiState = viewmodel.uiState
    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = ""
                    )
                }
            }, title = {
                Text(text = viewmodel.nickName)
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
                    Chat(uiState, image = image, onSearch = onSearch, onChat = onChat)
                }

                is ChatUiState.Error -> {

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
                ChatItem(uiState.chatItems[it], image = image, onClick = onChat)
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
fun ChatItem(
    uiState: ChatItemUiState,
    onClick: (Int) -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick.invoke(uiState.id) }) {
        Row(
            Modifier.height(70.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            image.invoke(
                Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0x11000000)),
                uiState.profileUrl,
                30.dp,
                30.dp,
                ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = uiState.nickName)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = uiState.seenTime)
            }
        }
        IconButton(modifier = Modifier.align(Alignment.CenterEnd),
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
    ChatScreen(onClose = {}, onChat = {}, onSearch = {})
}

@Preview(showBackground = true)
@Composable
fun ChatItemPreview() {
    ChatItem(ChatItemUiState(0, "Torang", "10:00", ""), onClick = {})
}