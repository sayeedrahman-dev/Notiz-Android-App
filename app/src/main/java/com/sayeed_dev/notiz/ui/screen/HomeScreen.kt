package com.sayeed_dev.notiz.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sayeed_dev.notiz.model.Note
import com.sayeed_dev.notiz.ui.components.NoteCard
import com.sayeed_dev.notiz.ui.theme.BackgroundCream
import com.sayeed_dev.notiz.ui.theme.ButtonColor
import com.sayeed_dev.notiz.ui.viewmodel.NoteViewModel

@Composable
fun HomeScreen(
    onAddNoteClick: () -> Unit,
    onNoteClick: (Note) -> Unit,
    viewModel : NoteViewModel = viewModel()
) {
    val context = LocalContext.current
    var lastBackPressTime by remember { mutableStateOf(0L) }

    // Double tap to exit logic
    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackPressTime < 2000) {
            (context as? android.app.Activity)?.finish()
        } else {
            android.widget.Toast.makeText(context, "Press back again to exit", android.widget.Toast.LENGTH_SHORT).show()
            lastBackPressTime = currentTime
        }
    }

    // 🚀 ১. ভিউমডেল থেকে সার্চ টেক্সট এবং লাইভ নোট লিস্ট রিড করা হচ্ছে
    val searchText by viewModel.searchQuery.collectAsState()
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())

    // 🚀 মেমোরি বাঁচানোর জন্য derivedStateOf ব্যবহার করা হয়েছে
    val pinnedNotes by remember(notes) {
        derivedStateOf { notes.filter { it.isPinned } }
    }
    val recentNotes by remember(notes) {
        derivedStateOf { notes.filter { !it.isPinned } }
    }

    Scaffold(
        containerColor = BackgroundCream,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.CenterStart)
                        .padding(top = 10.dp)
                )
                Text(
                    text = "NOTIz",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 40.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick,
                containerColor = ButtonColor,
                contentColor = Color.White,
                shape = RoundedCornerShape(100.dp),
                modifier = Modifier.padding(bottom = 40.dp, end = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 🚀 ২. সার্চ বারকে ভিউমডেলের সাথে কানেক্ট করা হলো
            OutlinedTextField(
                value = searchText, 
                onValueChange = { viewModel.onSearchQueryChanged(it) }, 
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                placeholder = { Text("Search notes", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = if (searchText.isEmpty()) {
                    { Icon(Icons.Default.Search, null, tint = Color.Gray) }
                } else null,
                trailingIcon = if (searchText.isNotEmpty()) {
                    {
                        IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color.Gray)
                        }
                    }
                } else null,
                shape = RoundedCornerShape(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                singleLine = true
            )

            if (notes.isEmpty()) {
                EmptyNotesState()
            } else {
                NotesContentWithSections(pinnedNotes, recentNotes, onNoteClick)
            }
        }
    }
}

@Composable
fun NotesContentWithSections(
    pinnedNotes: List<Note>,
    recentNotes: List<Note>,
    onNoteClick: (Note) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        // --- Pinned Notes Section (সবার উপরে) ---
        if (pinnedNotes.isNotEmpty()) {
            Text(text = "Pinned Notes", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            
            pinnedNotes.chunked(3).forEach { rowNotes ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowNotes.forEach { note ->
                        NoteCard(
                            note = note, 
                            modifier = Modifier.weight(1f),
                            onClick = { onNoteClick(note) }
                        )
                    }
                    repeat(3 - rowNotes.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (pinnedNotes.isNotEmpty() && recentNotes.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- Recent Notes Section ---
        if (recentNotes.isNotEmpty()) {
            Text(text = "Recent Notes", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            
            recentNotes.chunked(3).forEach { rowNotes ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowNotes.forEach { note ->
                        NoteCard(
                            note = note, 
                            modifier = Modifier.weight(1f),
                            onClick = { onNoteClick(note) }
                        )
                    }
                    repeat(3 - rowNotes.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun EmptyNotesState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = com.sayeed_dev.notiz.R.drawable.emptyicon),
            contentDescription = null,
            modifier = Modifier.size(240.dp),
            tint = Color.Unspecified
        )
        Text(text = "No notes yet", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        Text(text = "Tap + to create your first note", color = Color.Gray)
    }
}

@Preview(showBackground = true, name = "Home with Notes")
@Composable
fun HomeScreenPreview() {
    HomeScreen(onAddNoteClick = {}, onNoteClick = {})
}
