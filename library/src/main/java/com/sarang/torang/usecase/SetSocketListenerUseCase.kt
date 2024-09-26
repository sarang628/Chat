package com.sarang.torang.usecase

interface SetSocketListenerUseCase {
    fun invoke(webSocketListener: WebSocketListener)
}