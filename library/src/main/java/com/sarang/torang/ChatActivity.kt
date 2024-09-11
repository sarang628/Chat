package com.sarang.torang

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.compose.ChatScreenPreview

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatScreenPreview1()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ChatScreenPreview123(
        profileUrl: String,
        image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(bottom = 10.dp, left = 8.dp, right = 8.dp),
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
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
                                    text = "nickName", fontSize = 14.sp, style = TextStyle(
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        )
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "id", fontSize = 14.sp, style = TextStyle(
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
                            Icon(imageVector = Icons.Default.Call, contentDescription = "")
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Face, contentDescription = "")
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
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Face, contentDescription = "")
                        }
                    },
                    trailingIcon = {
                        Row {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Face, contentDescription = "")
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Face, contentDescription = "")
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Face, contentDescription = "")
                            }
                        }
                    },
                    shape = CircleShape,
                    colors = TextFieldDefaults.colors(
                        //backgroundColor = Color.Transparent, // 배경 투명
                        focusedIndicatorColor = Color.Transparent,  // 포커스 시 하단 선 제거
                        unfocusedIndicatorColor = Color.Transparent  // 비포커스 시 하단 선 제거
                    )
                )
            }
        }
    }

    @Preview
    @Composable
    fun ChatScreenPreview1(
    ) {
        ChatScreenPreview123("", { _, _, _, _, _ -> })
    }
}


