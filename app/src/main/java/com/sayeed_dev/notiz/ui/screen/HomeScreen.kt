package com.sayeed_dev.notiz.ui.screen


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayeed_dev.notiz.model.Note
import com.sayeed_dev.notiz.ui.components.NoteCard
import com.sayeed_dev.notiz.ui.theme.BackgroundCream
import com.sayeed_dev.notiz.ui.theme.ButtonColor

@Composable
fun HomeScreen(onAddNoteClick: () -> Unit) {
    val context = LocalContext.current
    var lastBackPressTime by remember { mutableStateOf(0L) }

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackPressTime < 2000) {
            (context as? android.app.Activity)?.finish()
        } else {
            android.widget.Toast.makeText(context, "Press back again to exit", android.widget.Toast.LENGTH_SHORT).show()
            lastBackPressTime = currentTime
        }
    }
    // 1. Dummy Data with Pinned logic
    val notes = listOf(
        Note(1, "The time", "Meeting with team at 10 AM", System.currentTimeMillis(), isPinned = true),
        Note(2, "Idea", "Build a minimal note app with Buddy", System.currentTimeMillis(), isPinned = true),
        Note(3, "Bazaar List", "Egg, Milk, Potato, Bread...", System.currentTimeMillis()),
        Note(4, "Reminder", "Submit the project by tonight.", System.currentTimeMillis()),
        Note(5, "New Idea", "Logic for multiple notes is working!", System.currentTimeMillis(), isPinned = false)
    )

    // Separate notes into two lists
    val pinnedNotes = notes.filter { it.isPinned }.take(6)
    val recentNotes = notes.filter { !it.isPinned }.take(6)

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
                    "Menu",
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
                onClick = { onAddNoteClick() }, // CONNECTED TO NAVIGATION
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
            // 2. Search Bar Section
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                placeholder = { Text("Search notes", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.Gray) },
                shape = RoundedCornerShape(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                singleLine = true
            )

            // 3. Conditional Content
            if (notes.isEmpty()) {
                EmptyNotesState()
            } else {
                NotesContentWithSections(pinnedNotes, recentNotes)
            }
        }
    }
}

@Composable
fun NotesContentWithSections(pinnedNotes: List<Note>, recentNotes: List<Note>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
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
                        NoteCard(note = note, modifier = Modifier.weight(1f))
                    }
                    repeat(3 - rowNotes.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Pinned Notes Section ---
        if (pinnedNotes.isNotEmpty()) {
            Text(text = "Pinned Notes", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            
            pinnedNotes.chunked(3).forEach { rowNotes ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowNotes.forEach { note ->
                        NoteCard(note = note, modifier = Modifier.weight(1f))
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
    HomeScreen(onAddNoteClick = {})
}
