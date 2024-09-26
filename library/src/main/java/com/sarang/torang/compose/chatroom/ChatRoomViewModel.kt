package com.sarang.torang.compose.chatroom

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.usecase.GetChatRoomUseCase
import com.sarang.torang.usecase.IsSignInUseCase
import com.sarang.torang.usecase.LoadChatRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val getChatRoomUseCase: GetChatRoomUseCase,
    private val loadChatRoomUseCase: LoadChatRoomUseCase,
    private val isSignInUseCase: IsSignInUseCase,
) : ViewModel() {

    var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading)
        private set

    var nickName by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            getChatRoomUseCase.invoke().collect { chatRooms ->
                Log.d("__ChatRoomViewModel", "received chatRooms : $chatRooms")
                uiState = ChatUiState.Success(chatRooms)
            }
        }
        viewModelScope.launch {
            isSignInUseCase.invoke().collect {
                if (!it) {
                    uiState = ChatUiState.Logout
                } else {
                    loadChatRoomUseCase.invoke()
                }
            }
        }
    }

    suspend fun refresh() {
        loadChatRoomUseCase.invoke()
    }
}