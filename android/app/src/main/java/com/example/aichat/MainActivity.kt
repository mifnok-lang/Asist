package com.example.aichat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm = ChatViewModel()

        setContent {
            MaterialTheme {
                Column(Modifier.fillMaxSize().padding(16.dp)) {
                    Text("AI Assistant")
                    Spacer(Modifier.height(8.dp))

                    Text(vm.statsText.value)
                    Button(onClick = { vm.refreshStats() }) { Text("Refresh stats") }

                    Spacer(Modifier.height(12.dp))

                    LazyColumn(Modifier.weight(1f)) {
                        items(vm.messages) { msg ->
                            Text("${msg.role}: ${msg.content}")
                            Spacer(Modifier.height(6.dp))
                        }
                    }

                    OutlinedTextField(
                        value = vm.input.value,
                        onValueChange = { vm.input.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Message") }
                    )

                    Spacer(Modifier.height(8.dp))

                    Button(onClick = { vm.send() }, modifier = Modifier.fillMaxWidth()) {
                        Text("Send")
                    }
                }
            }
        }
    }
}
