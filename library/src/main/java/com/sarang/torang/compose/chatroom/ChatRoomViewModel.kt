package com.sarang.torang.compose.chatroom

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.usecase.DeleteChatRoomUseCase
import com.sarang.torang.usecase.GetChatRoomUseCase
import com.sarang.torang.usecase.IsSignInUseCase
import com.sarang.torang.usecase.LoadChatRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val getChatRoomUseCase: GetChatRoomUseCase,
    private val loadChatRoomUseCase: LoadChatRoomUseCase,
    private val isSignInUseCase: IsSignInUseCase,
    private val deleteChatRoomUseCase: DeleteChatRoomUseCase
) : ViewModel() {

    var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading); private set
    var nickName by mutableStateOf(""); private set

    val uiState2: StateFlow<ChatUiState> =
        combine(
            isSignInUseCase.invoke(),          // Flow<Boolean>
            getChatRoomUseCase.invoke()        // Flow<List<ChatItem>>
        ) { isLoggedIn, chatRoom ->
            when {
                !isLoggedIn -> ChatUiState.Logout
                chatRoom.isEmpty() -> ChatUiState.Error("채팅방이 없습니다.")
                else -> ChatUiState.Success(chatItems = chatRoom)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ChatUiState.Loading
        )

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

    fun deleteRoom(roomId: Int) {
        viewModelScope.launch {
            deleteChatRoomUseCase.invoke(roomId)
        }
    }
}