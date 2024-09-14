package com.sarang.torang.usecase

interface GetUserOrCreateRoomByUserIdUseCase {
    suspend fun invoke(userId: Int): Int
}