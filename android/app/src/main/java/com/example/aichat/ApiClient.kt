package com.example.aichat

import com.example.aichat.model.ChatRequest
import com.example.aichat.model.ChatResponse
import com.example.aichat.model.StatsResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import com.google.gson.Gson

object ApiClient {
    private val client = OkHttpClient()
    private val gson = Gson()
    private const val BASE_URL = "http://10.0.2.2:8000"

    fun sendMessage(message: String): ChatResponse? {
        val json = gson.toJson(ChatRequest(message))
        val body = json.toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("$BASE_URL/chat")
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null
            return gson.fromJson(response.body?.string(), ChatResponse::class.java)
        }
    }

    fun getStats(): StatsResponse? {
        val request = Request.Builder()
            .url("$BASE_URL/stats")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null
            return gson.fromJson(response.body?.string(), StatsResponse::class.java)
        }
    }
}
