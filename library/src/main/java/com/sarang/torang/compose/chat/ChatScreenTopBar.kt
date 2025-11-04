package com.sarang.torang.compose.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.R

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatScreenTopBar(
    onBack: () -> Unit = {},
    uiState: ChatUiState.Success = ChatUiState.Success(),
) {
    TopAppBar(navigationIcon = {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = ""
            )
        }
    },
        title = {
            Row(Modifier.height(50.dp)) {
                if (!uiState.isMultiple) {
                    LocalChatImageLoader.current.invoke(
                        ChatImageLoaderData(
                            modifier = Modifier
                                .layoutId("image")
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color(0x11000000)),
                            url = uiState.profileUrl,
                            iconSize = 30.dp,
                            progressSize = 30.dp,
                            contentScale = ContentScale.Crop
                        )
                    )
                } else {
                    Box(modifier = Modifier.layoutId("image")) {
                        LocalChatImageLoader.current.invoke(
                            ChatImageLoaderData(
                            modifier = Modifier
                                .layoutId("image")
                                .size(50.dp)
                                .padding(end = 8.dp, bottom = 8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEEEEEE)),
                            url = uiState.profileUrl,
                            iconSize = 30.dp,
                            progressSize = 30.dp,
                            contentScale = ContentScale.Crop
                            )
                        )
                        LocalChatImageLoader.current.invoke(
                            ChatImageLoaderData(
                            modifier = Modifier
                                .layoutId("image")
                                .size(50.dp)
                                .padding(start = 8.dp, top = 5.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEEEEEE)),
                            url = uiState.user[1].profileUrl,
                            iconSize = 30.dp,
                            progressSize = 30.dp,
                            contentScale = ContentScale.Crop
                            )
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
}