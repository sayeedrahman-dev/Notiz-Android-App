package com.sayeed_dev.notiz.ui.components

import android.R.attr.content
import android.R.attr.id
import android.R.attr.title
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayeed_dev.notiz.model.Note
import com.sayeed_dev.notiz.ui.theme.PrimaryDark
@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
 ){
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            //title section
            Text(
                text = note.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            //content section
            Text(
                text = note.content,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )
            //footer section
            Text(
                text = "23 July",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun NoteCardPreview(){
    val  dummyNote = Note(
        id = 1,
        title = "The Time",
        content = "Life is short, make it worth it by learning new things everyday.",
        timestamp = System.currentTimeMillis()
    )
    NoteCard(note = dummyNote)
}