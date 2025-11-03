package com.sarang.torang.compose.chat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sarang.torang.R

@Composable
internal fun ChatScreenInput(
    modifier: Modifier = Modifier,
    uiState: ChatUiState.Success,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onPicture: () -> Unit,
) {
    TextField(value = uiState.message,
        onValueChange = onValueChange,
        modifier = modifier,
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
                    IconButton(onClick = onPicture) {
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