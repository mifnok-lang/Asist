package com.example.aichat.model

data class ChatMessage(
    val role: String,
    val content: String
)

data class ChatRequest(
    val message: String
)

data class ChatResponse(
    val reply: String,
    val history: List<ChatMessage>
)

data class StatsResponse(
    val total_messages: Int,
    val status: String
)
