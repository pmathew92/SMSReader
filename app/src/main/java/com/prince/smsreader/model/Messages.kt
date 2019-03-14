package com.prince.smsreader.model

data class Messages(
    val id: String,
    val date: String, val number: String,
    val message: String,
    val newMessage: Boolean = false
)