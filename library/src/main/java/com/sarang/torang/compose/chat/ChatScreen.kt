package com.sarang.torang.compose.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.R

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    profileUrl: String = "",
    onBack: () -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
) {
    val uiState = viewModel.uiState
    ChatScreen(uiState = uiState, profileUrl = profileUrl, image = image, onBack = onBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(
    uiState: ChatUiState,
    profileUrl: String,
    onBack: () -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
) {
    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 16.dp, left = 8.dp, right = 8.dp),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                title = {
                    Row {
                        image.invoke(
                            Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color(0x11000000)),
                            profileUrl,
                            30.dp,
                            30.dp,
                            ContentScale.Crop
                        )
                        Column {
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
                },
                actions = {
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
            TextField(
                value = "",
                onValueChange = {},
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                leadingIcon = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            tint = Color.White,
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.camera1),
                            contentDescription = ""
                        )
                    }
                },
                trailingIcon = {
                    Row {
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
                    }
                },
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,  // 포커스 시 하단 선 제거
                    unfocusedIndicatorColor = Color.Transparent  // 비포커스 시 하단 선 제거
                ),
                placeholder = {
                    Text(text = "Message...")
                }
            )
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview1(
) {
    ChatScreen(uiState = ChatUiState(), profileUrl = "", image = { _, _, _, _, _ -> }, onBack = {})
}