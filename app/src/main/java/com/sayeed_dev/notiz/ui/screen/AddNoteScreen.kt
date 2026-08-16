package com.sayeed_dev.notiz.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    initialId: Int = 0,
    viewModel: com.sayeed_dev.notiz.ui.viewmodel.NoteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBackClick: () -> Unit,
    onSaveClick: (String, String, Boolean) -> Unit,
    onDeleteClick: () -> Unit = {}
) {
    // 1. States for Input and UI logic
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isPinned by remember { mutableStateOf(false) }

    // যদি এডিট মুড হয়, ডাটাবেস থেকে লোড করো
    LaunchedEffect(initialId) {
        if (initialId != 0) {
            val note = viewModel.getNoteById(initialId)
            note?.let {
                title = it.title
                content = it.content
                isPinned = it.isPinned
            }
        }
    }
    
    var showExitDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val isEditMode = initialId != 0
    val screenHeader = if (!isEditMode) "New Note" else "Edit Note"

    // Logic: Back button block kora jate data loss na hoy
    BackHandler {
        if (title.isNotEmpty() || content.isNotEmpty()) {
            showExitDialog = true
        } else {
            onBackClick()
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
                    IconButton(onClick = {
                        if (title.isNotEmpty() || content.isNotEmpty()) {
                            showExitDialog = true
                        } else {
                            onBackClick()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(28.dp),
                            tint = PrimaryDark
                        )
                    }

                    // Dynamic Title (New Note / Edit Note)
                    Text(
                        text = screenHeader,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryDark,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // --- Professional Options Row ---
                    
                    // 1. Pin Icon (Toggle logic)
                    IconButton(onClick = { isPinned = !isPinned }) {
                        Icon(
                            imageVector = if (isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                            contentDescription = "Pin",
                            modifier = Modifier.size(24.dp),
                            tint = if (isPinned) Color(0xFFE91E63) else PrimaryDark 
                        )
                    }

                    // 2. Delete Icon (Shudhu Edit mode-e dekhabe)
                    if (isEditMode) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier.size(26.dp),
                                tint = Color.Gray
                            )
                        }
                    }

                    // 3. Save Icon
                    IconButton(onClick = { onSaveClick(title, content, isPinned) }) {
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
                    Text("Note Title", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryDark),
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

        // --- Dialogs Section ---

        // 1. Exit Confirmation Dialog
        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                containerColor = BackgroundCream,
                shape = RoundedCornerShape(28.dp),
                title = {
                    Text("Save changes?", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryDark)
                },
                confirmButton = {
                    TextButton(onClick = {
                        onSaveClick(title, content, isPinned)
                        showExitDialog = false
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

        // 2. Delete Confirmation Dialog
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                containerColor = Color.White,
                shape = RoundedCornerShape(28.dp),
                title = {
                    Text("Delete this note?", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color.Red)
                },
                text = {
                    Text("This action cannot be undone. Are you sure?", fontSize = 14.sp)
                },
                confirmButton = {
                    Button(
                        onClick = { 
                            onDeleteClick() 
                            showDeleteDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(100.dp)
                    ) {
                        Text("Delete", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel", color = Color.Gray)
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
        onSaveClick = { _, _, _ -> }
    )
}
