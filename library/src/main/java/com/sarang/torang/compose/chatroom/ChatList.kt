package com.sarang.torang.compose.chatroom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun ChatList(
    uiState: ChatUiState.Success = ChatUiState.Success(),
    onSearch: () -> Unit = {},
    onChat: (Int) -> Unit = {},
) {
    var text by remember { mutableStateOf("") }

    Column {
        LazyColumn {
            items(3) {
                when (it) {
                    0 -> {
                        SearchBar(
                            onSearch = onSearch,
                            text = text,
                            onText = { text = it }
                        )
                    }
                    1 -> {
                        YourNote()
                    }
                    2 -> {
                        Tabs()
                    }
                }
            }
            items(uiState.chatItems.size) {
                ChatRoomItem(uiState.chatItems[it], onClick = onChat)
            }
        }
    }
}

@Preview
@Composable
private fun SearchBar(
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