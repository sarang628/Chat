package com.sarang.torang.compose.chatroom

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.data.ChatUser

@Preview(showBackground = true)
@Composable
fun ChatList(
    uiState: ChatUiState.Success = ChatUiState.Success(),
    onSearch: () -> Unit = {},
    onChat: (Int) -> Unit = {},
    onLongClick: (Int) -> Unit = {},
) {
    var text by remember { mutableStateOf("") }
    val animationSpec = spring<IntOffset>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMedium
    )
    Box(Modifier.fillMaxSize()){
        Column(Modifier.fillMaxSize()) {
            SearchBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                onSearch = onSearch,
                text = text,
                onText = { text = it }
            )
            YourNote()
            if (!uiState.chatItems.isEmpty()){
                LazyColumn {
                    items(
                        items = uiState.chatItems,
                        key = { it.id }
                    ) { item ->
                        ChatRoomItem(
                            modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null, placementSpec = animationSpec),
                            uiState = item,
                            onClick = onChat,
                            onLongClick = onLongClick
                        )
                    }
                }
            }
        }
        if (uiState.chatItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()){
                Text(modifier = Modifier.align(Alignment.Center), text = "채팅방이 없습니다.")
            }
        }
    }
}

@Preview
@Composable
private fun SearchBar(
    modifier : Modifier = Modifier,
    onSearch: () -> Unit = {},
    text : String = "",
    onText : (String) -> Unit = {}
){
    Row(
        modifier = Modifier
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
        BasicTextField(
            value = text,
            onValueChange = { onText(it) },
            decorationBox = {
                if(text.isEmpty()){
                    Text("search")
                }
                else {
                    it()
                }
            }
        )
    }
}

@Preview
@Composable
fun YourNote(){
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
}


@Preview(showBackground = true)
@Composable
fun ChatListPreview(){
    var uiState by remember { mutableStateOf(ChatUiState.Success(/*Preview*/
        chatItems = listOf(
            ChatRoomUiState(
                1,
                "10min",
                listOf(
                    ChatUser(nickName = "nickName1", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                )
            ),
            ChatRoomUiState(
                2,
                "15min",
                listOf(
                    ChatUser(nickName = "nickName2", profileUrl = "1", id = "id"),
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
                    ChatUser(nickName = "nickName3", profileUrl = "1", id = "id"),
                    ChatUser(nickName = "nickName", profileUrl = "1", id = "id"),
                )
            ),
            ChatRoomUiState(
                4,
                "26min",
                listOf(ChatUser(nickName = "nickName4", profileUrl = "1", id = "id"))
            ),
        )
    )) }
    ChatList(uiState = uiState)

    Button({
        uiState = uiState.copy(
            chatItems = uiState.chatItems.filter { it.id != 2 }
        )
    }) {
        Text("Delete")
    }
}