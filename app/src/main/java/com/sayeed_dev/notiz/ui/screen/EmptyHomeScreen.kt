package com.sayeed_dev.notiz.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayeed_dev.notiz.ui.theme.BackgroundCream
import com.sayeed_dev.notiz.ui.theme.ButtonColor

@Composable
fun EmptyHomeScreen(onNavigateToMenu: () -> Unit = {}) {
    Scaffold(
        containerColor = BackgroundCream,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp, 12.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Menu,
                    "Menu",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterStart)
                )
                Text(
                    text = "NOTIz",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 30.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = ButtonColor,
                contentColor = Color.White,
                shape = RoundedCornerShape(100.dp),
                modifier = Modifier.padding(bottom = 60.dp, end = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ){paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            //Search Ber
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                placeholder = {
                    Text(
                        text = "Search notes",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(30.dp)
                    )
                },
                shape = RoundedCornerShape(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(60.dp))

            Icon(
                painter = painterResource(id = com.sayeed_dev.notiz.R.drawable.emptyicon),
                contentDescription = "Empty State Icon",
                modifier = Modifier.size(240.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "No notes yet",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tap + to create your first note",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyHomeScreenPreview() {
    EmptyHomeScreen()
}