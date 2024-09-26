package com.sarang.torang.usecase

interface WebSocketListener {
    /**
     * Invoked when a web socket has been accepted by the remote peer and may begin transmitting
     * messages.
     */
    fun onOpen()


    /** Invoked when a text (type `0x1`) message has been received. */
    fun onMessage()

    /**
     * Invoked when the remote peer has indicated that no more incoming messages will be transmitted.
     */
    fun onClosing()

    /**
     * Invoked when both peers have indicated that no more messages will be transmitted and the
     * connection has been successfully released. No further calls to this listener will be made.
     */
    fun onClosed()

    /**
     * Invoked when a web socket has been closed due to an error reading from or writing to the
     * network. Both outgoing and incoming messages may have been lost. No further calls to this
     * listener will be made.
     */
    fun onFailure()
}