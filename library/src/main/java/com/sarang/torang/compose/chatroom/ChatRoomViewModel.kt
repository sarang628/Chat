package com.sarang.torang.compose.chatroom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.usecase.GetChatRoomUseCase
import com.sarang.torang.usecase.LoadChatRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val getChatRoomUseCase: GetChatRoomUseCase,
    private val loadChatRoomUseCase: LoadChatRoomUseCase,
) : ViewModel() {

    var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading)
        private set

    var nickName by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            loadChatRoomUseCase.invoke()
        }
        viewModelScope.launch {
            getChatRoomUseCase.invoke().collect {
                uiState = ChatUiState.Success(it)
            }
        }
    }

    suspend fun refresh() {
        loadChatRoomUseCase.invoke()
    }
}