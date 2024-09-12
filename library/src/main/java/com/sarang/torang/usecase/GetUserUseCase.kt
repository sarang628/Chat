package com.sarang.torang.usecase

import com.sarang.torang.data.ChatUser

interface GetUserUseCase {
    fun invoke(): ChatUser
}