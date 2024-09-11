package com.sarang.torang

import android.os.Bundle
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.compose.ChatScreen
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.providePullToRefresh
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import com.sarang.torang.ui.theme.ChatTheme
import com.sryang.library.pullrefresh.RefreshIndicatorState
import com.sryang.library.pullrefresh.rememberPullToRefreshState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val height = LocalConfiguration.current.screenHeightDp.dp
            val state = rememberPullToRefreshState()
            val coroutine = rememberCoroutineScope()
            ChatTheme {
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
                                    onChat = {},
                                    pullToRefreshLayout = providePullToRefresh(state),
                                    image = provideTorangAsyncImage(),
                                    onRefresh = {
                                        coroutine.launch {
                                            state.updateState(RefreshIndicatorState.Default)
                                        }
                                    }
                                )
                            }
                            LoginRepositoryTest(loginRepository = loginRepository)
                        }
                    }
                }
            }
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