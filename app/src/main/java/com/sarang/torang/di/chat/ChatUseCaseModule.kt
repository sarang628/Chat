package com.sarang.torang.di.chat

import com.sarang.torang.compose.ChatRoomUiState
import com.sarang.torang.compose.ChatUiState
import com.sarang.torang.data.entity.ChatRoomEntity
import com.sarang.torang.data.remote.response.AlarmAlarmModel
import com.sarang.torang.repository.ChatRepository
import com.sarang.torang.usecase.GetChatRoomUseCase
import com.sarang.torang.usecase.GetChatUseCase
import com.sarang.torang.usecase.LoadChatRoomUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ChatUseCaseModule {
    @Singleton
    @Provides
    fun provideGetChatRoomUseCase(chatRepository: ChatRepository): GetChatRoomUseCase {
        return object : GetChatRoomUseCase {
            override fun invoke(): Flow<List<ChatRoomUiState>> {
                return chatRepository.getChatRoom().map { list ->
                    list.map { chatRoomEntity ->
                        ChatRoomUiState(
                            chatRoomEntity.roomId,
                            "",
                            "",
                            ""
                        )
                    }
                }
            }
        }
    }

    @Singleton
    @Provides
    fun provideLoadChatRoomUseCase(chatRepository: ChatRepository): LoadChatRoomUseCase {
        return object : LoadChatRoomUseCase {
            override suspend fun invoke() {
                return chatRepository.loadChatRoom()
            }
        }
    }

    @Singleton
    @Provides
    fun provideGetChatUseCase(chatRepository: ChatRepository): GetChatUseCase {
        return object : GetChatUseCase {
            override fun invoke(roomId: Int): Flow<List<ChatUiState>> {
                return chatRepository.getContents(roomId).map { list ->
                    list.map { chatEntity ->
                        ChatUiState.Success()
                    }
                }
            }
        }
    }
}