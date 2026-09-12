package com.example.aichat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

data class UiMessage(val role: String, val content: String)

class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<UiMessage>()
    val input = mutableStateOf("")
    val statsText = mutableStateOf("")

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun send() {
        val text = input.value.trim()
        if (text.isEmpty()) return

        messages.add(UiMessage("user", text))
        input.value = ""

        scope.launch {
            val response = ApiClient.sendMessage(text)
            response?.let {
                withContext(Dispatchers.Main) {
                    messages.add(UiMessage("assistant", it.reply))
                }
            }
        }
    }

    fun refreshStats() {
        scope.launch {
            val stats = ApiClient.getStats()
            withContext(Dispatchers.Main) {
                statsText.value = stats?.let { "Messages: ${it.total_messages}" } ?: "No stats"
            }
        }
    }
}
