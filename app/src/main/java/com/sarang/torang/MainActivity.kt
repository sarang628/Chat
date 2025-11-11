package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.chatroom.ChatRoomScreen
import com.sarang.torang.di.chat_di.provideChatScreen
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.test.LoginRepositoryTest
import com.sarang.torang.usecase.GetUserOrCreateRoomByUserIdUseCase
import com.sryang.library.pullrefresh.rememberPullToRefreshState
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var loginRepository: LoginRepository
    @Inject lateinit var createRoomByUserIdUseCase: GetUserOrCreateRoomByUserIdUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TorangTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        ChatNavigation(loginRepository = loginRepository)
                    }
                }
            }
        }
    }
}



@Composable
fun ChatNavigation(loginRepository : LoginRepository){
    val navController = rememberNavController()
    val state = rememberPullToRefreshState()
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "menu"){
        composable("menu"){
            Column {
                Button({navController.navigate("ChatScreen")}) {
                    Text("ChatScreen")
                }
                Button({navController.navigate("LoginRepositoryTest")}) {
                    Text("LoginRepositoryTest")
                }
            }
        }
        composable("LoginRepositoryTest"){
            LoginRepositoryTest(loginRepository = loginRepository)
        }
        composable("ChatScreen"){
            provideChatScreen().invoke()
        }
        composable("CreateOneToOneChatRoomTest"){
            CreateOneToOneChatRoomTest(onClick = {
                coroutine.launch {
                    /*try {
                        val roomId = createRoomByUserIdUseCase.invoke(it)
                        startActivity(
                            Intent(
                                this@MainActivity,
                                ChatActivity::class.java
                            ).apply {
                                putExtra("roomId", roomId)
                            }
                        )

                    } catch (e: Exception) {
                        Log.e("__MainActivity", e.message.toString())
                    }*/
                }
            })
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
fun ChatRoomScreenPreview() {
    TorangTheme {
        ChatRoomScreen(
            onClose = {},
            onChat = {},
            onSearch = {},
            onRefresh = {})
    }
}