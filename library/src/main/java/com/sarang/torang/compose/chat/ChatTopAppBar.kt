package com.sarang.torang.compose.chat

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ChatTopAppBar(
    onClose : ()->Unit = {},
    nickName : String = ""
) {
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
}