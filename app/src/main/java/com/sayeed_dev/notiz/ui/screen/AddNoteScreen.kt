package com.sayeed_dev.notiz.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.automirrored.filled.FormatAlignLeft
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.sayeed_dev.notiz.ui.theme.BackgroundCream
import com.sayeed_dev.notiz.ui.theme.PrimaryDark
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddNoteScreen(
    initialId: Int = 0,
    viewModel: com.sayeed_dev.notiz.ui.viewmodel.NoteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBackClick: () -> Unit,
    onSaveClick: (String, String, Boolean, Int) -> Unit,
    onDeleteClick: () -> Unit = {}
) {
    val noteColors = listOf(
        Color.White,
        Color(0xFFFFCDD2),
        Color(0xFFF1F8E9),
        Color(0xFFE1F5FE),
        Color(0xFFFFF9C4),
        Color(0xFFF3E5F5)
    )

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isPinned by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(noteColors[0]) }

    LaunchedEffect(initialId) {
        if (initialId != 0) {
            val note = viewModel.getNoteById(initialId)
            note?.let {
                title = it.title
                content = it.content
                isPinned = it.isPinned
                selectedColor = Color(it.color)
            }
        }
    }
    
    var showExitDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showMoreMenu by remember { mutableStateOf(false) }
    var showColorPalette by remember { mutableStateOf(false) }

    val isEditMode = initialId != 0
    val screenHeader = if (!isEditMode) "New Note" else "Edit Note"
    val currentTime = remember { SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date()) }

    // কিবোর্ড ওপেন আছে কিনা চেক করার লজিক
    val isKeyboardVisible = WindowInsets.ime.asPaddingValues().calculateBottomPadding() > 0.dp
    
    // 🚀 প্রো-ফিক্স: যদি মেনু বা প্যালেট ওপেন থাকে, তবে কিবোর্ড নেমে গেলেও টুলবার থাকবে
    val shouldShowToolbar = isKeyboardVisible || showMoreMenu || showColorPalette

    BackHandler {
        if (title.isNotEmpty() || content.isNotEmpty()) {
            showExitDialog = true
        } else {
            onBackClick()
        }
    }

    Scaffold(
        containerColor = selectedColor,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(Color.White.copy(alpha = 0.5f))
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (title.isNotEmpty() || content.isNotEmpty()) showExitDialog = true else onBackClick()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = PrimaryDark)
                    }

                    Text(screenHeader, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryDark)

                    Spacer(modifier = Modifier.weight(1f))

                    if (isEditMode) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Default.Delete, "Delete", tint = PrimaryDark)
                        }
                    }

                    IconButton(onClick = { onSaveClick(title, content, isPinned, selectedColor.toArgb()) }) {
                        Icon(Icons.Default.Check, "Save", modifier = Modifier.size(28.dp), tint = PrimaryDark)
                    }
                }
            }
        },
        bottomBar = {
            // 🚀 ফিক্সড লজিক: shouldShowToolbar ব্যবহার করা হলো
            if (shouldShowToolbar) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .background(BackgroundCream.copy(alpha = 0.95f))
                        .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .padding(vertical = 4.dp)
                ) {
                    if (showColorPalette) {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(noteColors) { color ->
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .border(1.dp, Color.Gray, CircleShape)
                                        .clickable { 
                                            selectedColor = color
                                            showColorPalette = false 
                                        }
                                )
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { /* Bold logic */ }) {
                            Icon(Icons.Default.FormatBold, "Bold", tint = PrimaryDark)
                        }
                        IconButton(onClick = { /* Italic logic */ }) {
                            Icon(Icons.Default.FormatItalic, "Italic", tint = PrimaryDark)
                        }
                        IconButton(onClick = { /* Color logic */ }) {
                            Icon(Icons.Default.FormatColorText, "Text Color", tint = PrimaryDark)
                        }
                        IconButton(onClick = { showColorPalette = !showColorPalette }) {
                            Icon(Icons.Default.Palette, "Theme", tint = PrimaryDark)
                        }
                        IconButton(onClick = { isPinned = !isPinned }) {
                            Icon(
                                imageVector = if (isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                                contentDescription = "Pin",
                                tint = if (isPinned) Color(0xFFE91E63) else PrimaryDark 
                            )
                        }
                        Box {
                            IconButton(onClick = { showMoreMenu = true }) {
                                Icon(Icons.Default.MoreVert, "More", tint = PrimaryDark)
                            }
                            DropdownMenu(
                                expanded = showMoreMenu,
                                onDismissRequest = { showMoreMenu = false },
                                properties = PopupProperties(focusable = false) // 🚀 এখানে ফিক্স! এটা কিবোর্ডকে তাড়াবে না
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Underline") },
                                    onClick = { showMoreMenu = false },
                                    leadingIcon = { Icon(Icons.Default.FormatUnderlined, null) }
                                )
                                DropdownMenuItem(
                                    text = { Text("Bullet List") },
                                    onClick = { showMoreMenu = false },
                                    leadingIcon = { Icon(Icons.AutoMirrored.Filled.FormatListBulleted, null) }
                                )
                                DropdownMenuItem(
                                    text = { Text("Align Left") },
                                    onClick = { showMoreMenu = false },
                                    leadingIcon = { Icon(Icons.AutoMirrored.Filled.FormatAlignLeft, null) }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("Undo") },
                                    onClick = { showMoreMenu = false },
                                    leadingIcon = { Icon(Icons.AutoMirrored.Filled.Undo, null) }
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold, color = PrimaryDark),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Text(
                text = "Last Edited: $currentTime",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
            )

            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("Start typing...", fontSize = 18.sp) },
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(fontSize = 18.sp, color = Color.DarkGray),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )
        }

        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                containerColor = BackgroundCream,
                shape = RoundedCornerShape(28.dp),
                title = { Text("Save changes?", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryDark) },
                confirmButton = {
                    TextButton(onClick = {
                        onSaveClick(title, content, isPinned, selectedColor.toArgb())
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

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                containerColor = Color.White,
                shape = RoundedCornerShape(28.dp),
                title = { Text("Delete this note?", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color.Red) },
                text = { Text("This action cannot be undone. Are you sure?", fontSize = 14.sp) },
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
        onSaveClick = { _, _, _, _ -> }
    )
}
