package com.sarang.torang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.compose.chatroom.ChatScreen
//import com.sarang.torang.di.chat_di.ChatActivity
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.providePullToRefresh
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import com.sarang.torang.usecase.GetUserOrCreateRoomByUserIdUseCase
import com.sryang.library.pullrefresh.RefreshIndicatorState
import com.sryang.library.pullrefresh.rememberPullToRefreshState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var createRoomByUserIdUseCase: GetUserOrCreateRoomByUserIdUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val height = LocalConfiguration.current.screenHeightDp.dp
            val state = rememberPullToRefreshState()
            val coroutine = rememberCoroutineScope()
            TorangTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            Box(modifier = Modifier.height(height))
                            {
                                ChatScreen(
                                    onClose = { /*TODO*/ },
                                    onSearch = { /*TODO*/ },
                                    onChat = {
                                        /*startActivity(
                                            Intent(
                                                this@MainActivity,
                                                ChatActivity::class.java
                                            ).apply {
                                                putExtra("roomId", it)
                                            }
                                        )*/
                                    },
                                    pullToRefreshLayout = providePullToRefresh(state),
                                    image = provideTorangAsyncImage(),
                                    onRefresh = {
                                        coroutine.launch {
                                            state.updateState(RefreshIndicatorState.Default)
                                        }
                                    }
                                )
                            }
                            CreateOneToOneChatRoomTest(onClick = {
                                coroutine.launch {
                                    try {
                                        val roomId = createRoomByUserIdUseCase.invoke(it)

                                        /*startActivity(
                                            Intent(
                                                this@MainActivity,
                                                ChatActivity::class.java
                                            ).apply {
                                                putExtra("roomId", roomId)
                                            }
                                        )*/

                                    } catch (e: Exception) {
                                        Log.e("__MainActivity", e.message.toString())
                                    }
                                }
                            })
                            LoginRepositoryTest(loginRepository = loginRepository)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun CreateOneToOneChatRoomTest(onClick: (Int) -> Unit) {
    Column {
        Button(onClick = { onClick.invoke(1) }) {
            Text(text = "user id 1")
        }
        Button(onClick = { onClick.invoke(2) }) {
            Text(text = "user id 2")
        }
        Button(onClick = { onClick.invoke(3) }) {
            Text(text = "user id 3")
        }
        Button(onClick = { onClick.invoke(4) }) {
            Text(text = "user id 4")
        }
        Button(onClick = { onClick.invoke(5) }) {
            Text(text = "user id 5")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    TorangTheme {
        ChatScreen(
            onClose = {},
            image = provideTorangAsyncImage(),
            onChat = {},
            onSearch = {},
            onRefresh = {})
    }
}