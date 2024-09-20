package com.sarang.torang.data

data class Chat(
    val message: String,
    val userName: String,
    val userId: Int,
    val profileUrl: String,
    val createDate: String,
    val isMe: Boolean,
    val isSending: Boolean,
)
