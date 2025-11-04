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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun Chat(
    uiState: ChatUiState.Success = ChatUiState.Success(),
    onSearch: () -> Unit = {},
    onChat: (Int) -> Unit = {},
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
                ChatRoomItem(uiState.chatItems[it], onClick = onChat)
            }
        }
    }
}