package com.sayeed_dev.notiz.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sayeed_dev.notiz.R
import com.sayeed_dev.notiz.model.Note
import com.sayeed_dev.notiz.ui.components.NoteCard
import com.sayeed_dev.notiz.ui.theme.BackgroundCream
import com.sayeed_dev.notiz.ui.theme.ButtonColor
import com.sayeed_dev.notiz.ui.theme.PrimaryDark
import com.sayeed_dev.notiz.ui.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddNoteClick: () -> Unit,
    onNoteClick: (Note) -> Unit,
    viewModel: NoteViewModel = viewModel()
) {
    // -----------------------------------------------------------
    // ১. ভেরিয়েবল এবং স্টেট এরিয়া (Variables & States)
    // -----------------------------------------------------------
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var lastBackPressTime by remember { mutableStateOf(0L) }

    // 🚀 ব্লার এর তীব্রতা অ্যানিমেট করা (ড্রয়ার খুললে ব্লার বাড়বে)
    val blurRadius by animateDpAsState(
        targetValue = if (drawerState.targetValue == DrawerValue.Open) 10.dp else 0.dp,
        label = "blur"
    )

    // 🚀 হোমে ঢোকার সাথে সাথে বা ফিরে আসার সাথে সাথে সার্চ ক্লিয়ার করার লজিক
    LaunchedEffect(Unit) {
        viewModel.onSearchQueryChanged("")
    }

    val searchText by viewModel.searchQuery.collectAsState()
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())

    val pinnedNotes by remember(notes) {
        derivedStateOf { notes.filter { it.isPinned } }
    }
    val recentNotes by remember(notes) {
        derivedStateOf { notes.filter { !it.isPinned } }
    }

    // -----------------------------------------------------------
    // ২. ব্যাক বাটন হ্যান্ডলার (Back Button Logic)
    // -----------------------------------------------------------
    BackHandler {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastBackPressTime < 2000) {
                (context as? android.app.Activity)?.finish()
            } else {
                android.widget.Toast.makeText(context, "Press back again to exit", android.widget.Toast.LENGTH_SHORT).show()
                lastBackPressTime = currentTime
            }
        }
    }

    // -----------------------------------------------------------
    // ৩. মূল স্লাইডিং মেনু বা ড্রয়ার (Main Navigation Drawer)
    // -----------------------------------------------------------
    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Transparent, // 🚀 স্ক্রিম ট্রান্সপারেন্ট রাখা হলো যাতে ব্লারটা ক্লিয়ার দেখা যায়
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White.copy(alpha = 0.45f), // আরও স্বচ্ছ করা হলো (Glass effect)
                drawerShape = RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp),
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
            ) {
                DrawerHeader() 
                
                Spacer(modifier = Modifier.height(16.dp))

                // মেনুর অপশনগুলো একটু স্টাইলিশ ভাবে সাজানো
                Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                    DrawerItem("All Notes", Icons.Default.Notes, isSelected = true) {
                        scope.launch { drawerState.close() }
                    }
                    DrawerItem("Settings", Icons.Default.Settings, isSelected = false) {
                        // Settings logic
                    }
                    DrawerItem("Logout", Icons.AutoMirrored.Filled.Logout, isSelected = false) {
                        // Logout logic
                    }
                }
            }
        }
    ) {
        // -----------------------------------------------------------
        // ৪. মেইন স্ক্রিন (Main Screen Content - Scaffold)
        // -----------------------------------------------------------
        Scaffold(
            modifier = Modifier.blur(blurRadius), // 🚀 এখানে ম্যাজিক! পেছনের কন্টেন্ট ব্লার হবে
            containerColor = BackgroundCream,
            topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "NOTIz",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        color = PrimaryDark
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            modifier = Modifier.size(28.dp),
                            tint = PrimaryDark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.statusBarsPadding()
            )
        },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAddNoteClick,
                    containerColor = ButtonColor,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(100.dp)
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
                // --- সার্চ বার (আপনার পছন্দের স্টাইলে) ---
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    placeholder = { Text("Search notes", color = Color.Gray, fontSize = 14.sp) },
                    leadingIcon = if (searchText.isEmpty()) {
                        {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier
                                    .padding(start = 8.dp) // লোগোটি একটু ডানে সরানো হয়েছে
                                    .size(22.dp)
                            )
                        }
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
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = PrimaryDark,
                        unfocusedTextColor = PrimaryDark
                    ),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 14.sp)
                )

                if (notes.isEmpty()) {
                    EmptyNotesState()
                } else {
                    NotesContentWithSections(pinnedNotes, recentNotes, onNoteClick)
                }
            }
        }
    }
}

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.1f)) // 🚀 নীল রঙ সরিয়ে হালকা স্বচ্ছ সাদা করা হলো
            .padding(24.dp)
            .padding(top = 30.dp)
    ) {
        // গোল প্রোফাইল ছবি (বর্ডারের জন্য একটু শ্যাডো দেওয়া হয়েছে)
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.5f))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), 
                contentDescription = "Profile",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        // টেক্সটগুলো গাঢ় কালো করা হলো যাতে পরিষ্কার বুঝা যায়
        Text(text = "Sayeed Dev", color = PrimaryDark, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        Text(text = "sayeed@notiz.com", color = Color.Gray, fontSize = 14.sp)
    }
}

@Composable
fun DrawerItem(label: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    NavigationDrawerItem(
        label = { Text(text = label) },
        selected = isSelected,
        onClick = onClick,
        icon = { Icon(imageVector = icon, contentDescription = label) },
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = ButtonColor.copy(alpha = 0.1f),
            selectedIconColor = ButtonColor,
            selectedTextColor = ButtonColor
        )
    )
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
                    repeat(3 - rowNotes.size) { Spacer(modifier = Modifier.weight(1f)) }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (pinnedNotes.isNotEmpty() && recentNotes.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
        }

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
                    repeat(3 - rowNotes.size) { Spacer(modifier = Modifier.weight(1f)) }
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
            painter = painterResource(id = R.drawable.emptyicon),
            contentDescription = null,
            modifier = Modifier.size(240.dp),
            tint = Color.Unspecified
        )
        Text(text = "No notes yet", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        Text(text = "Tap + to create your first note", color = Color.Gray)
    }
}
