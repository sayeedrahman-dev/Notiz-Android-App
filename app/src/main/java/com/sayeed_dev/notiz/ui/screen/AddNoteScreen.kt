package com.sayeed_dev.notiz.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayeed_dev.notiz.ui.theme.BackgroundCream
import com.sayeed_dev.notiz.ui.theme.PrimaryDark

@Composable
fun AddNoteScreen(
    onBackClick: () -> Unit,
    onSaveClick: (String, String) -> Unit
) {
    // 1. States for Input
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    BackHandler {
        // Jodi title ba content-e kichu likha thake, tobe dialog dekhaw
        if (title.isNotEmpty() || content.isNotEmpty()) {
            showDialog = true
        } else {
            onBackClick() // Khali thakle sorasori back jabe
        }
    }
    Scaffold(
        containerColor = BackgroundCream,
        topBar = {
            // Custom Professional TopBar for full control
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.White.copy(alpha = 0.7f))
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back Button
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(28.dp),
                            tint = PrimaryDark
                        )
                    }

                    // Spacing + Title
                    Text(
                        text = "New Note",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryDark,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Save Icon
                    IconButton(onClick = { onSaveClick(title, content) }) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save",
                            modifier = Modifier.size(32.dp),
                            tint = PrimaryDark
                        )
                    }
                }
            }
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            // Title Input
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = {
                    Text(
                        "Note Title",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                maxLines = 2,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Content Input
            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("Start writing your thoughts...", fontSize = 16.sp) },
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.DarkGray),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                containerColor = BackgroundCream,
                shape = RoundedCornerShape(28.dp),
                title = {
                    Text(
                        text = "Save this note?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryDark
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        onSaveClick(title, content)
                        showDialog = false
                    }) {
                        Text("Save", color = PrimaryDark, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { onBackClick() }) {
                        Text("Discard", color = Color.Gray)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddNoteScreenPreview() {
    AddNoteScreen(
        onBackClick = {},
        onSaveClick = { _, _ -> }
    )
}
