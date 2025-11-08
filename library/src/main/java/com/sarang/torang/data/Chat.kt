package com.sarang.torang.data

data class Chat(
    val message     : String    = "",
    val userName    : String    = "",
    val userId      : Int       = -1,
    val profileUrl  : String    = "",
    val createDate  : String    = "",
    val isMe        : Boolean   = true,
    val isSending   : Boolean   = false,
)
