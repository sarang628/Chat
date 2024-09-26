package com.sarang.torang.usecase

import kotlinx.coroutines.flow.Flow

interface IsSignInUseCase {
    fun invoke(): Flow<Boolean>
}