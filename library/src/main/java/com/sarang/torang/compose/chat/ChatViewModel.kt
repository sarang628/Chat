package com.sarang.torang.compose.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.usecase.GetChatUseCase
import com.sarang.torang.usecase.GetUserByRoomIdUseCase
import com.sarang.torang.usecase.LoadChatUseCase
import com.sarang.torang.usecase.SendChatUseCase
import com.sarang.torang.usecase.SetSocketCloseUseCase
import com.sarang.torang.usecase.SubScribeRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getUserUseCase: GetUserByRoomIdUseCase,
    private val sendChatUseCase: SendChatUseCase,
    private val getChatUseCase: GetChatUseCase,
    private val loadChatUseCase: LoadChatUseCase,
    private val setSetSocketCloseUseCase: SetSocketCloseUseCase,
    private val setSubScribeRoomUseCase: SubScribeRoomUseCase,
) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        Log.d("__ChatViewModel", "onCleared")
        if (uiState is ChatUiState.Success) {
            (uiState as ChatUiState.Success).let {
                setSetSocketCloseUseCase.invoke(it.roomId)
            }
        }
    }

    var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading)
        private set

    fun onMessageChange(message: String) {
        if (uiState is ChatUiState.Success) {
            (uiState as ChatUiState.Success).let {
                uiState = it.copy(message = message)
            }
        }
    }

    fun onSend() {
        if (uiState is ChatUiState.Success) {
            (uiState as ChatUiState.Success).let {
                Log.d("__ChatViewModel", "onSend : ${it.message}")
                viewModelScope.launch {
                    sendChatUseCase.invoke(it.roomId, it.message)
                    uiState = it.copy(message = "")
                }
            }
        }
    }

    fun loadUserByRoomId(roomId: Int) {
        Log.d("__ChatViewModel", "loadUserByRoomId : $roomId")
        viewModelScope.launch {
            loadChatUseCase.invoke(roomId)
        }
        viewModelScope.launch {
            getUserUseCase.invoke(roomId).collect {
                //Log.d("__ChatViewModel", "loaded user list : $it")
                if (uiState is ChatUiState.Loading)
                    uiState = ChatUiState.Success(
                        user = it ?: listOf(), id = "", roomId = roomId
                    )
                else if (uiState is ChatUiState.Success) {
                    uiState = (uiState as ChatUiState.Success).copy(user = it ?: listOf())
                }
            }
        }
        viewModelScope.launch {
            getChatUseCase.invoke(roomId).collect {
                //Log.d("__ChatViewModel", "loaded chat list : $it")
                if (uiState is ChatUiState.Loading)
                    uiState = ChatUiState.Success(
                        chats = it, id = "", roomId = roomId
                    )
                else if (uiState is ChatUiState.Success) {
                    uiState = (uiState as ChatUiState.Success).copy(chats = it)
                }
            }
        }

        viewModelScope.launch {
            setSubScribeRoomUseCase.invoke(roomId, viewModelScope).collect {
                Log.d("__ChatViewModel", "event : $it")
            }
        }
    }

    fun sendImages(images: List<String>) {
        if (uiState is ChatUiState.Success) {
            (uiState as ChatUiState.Success).let {
                viewModelScope.launch {

                }
            }
        }
    }
}