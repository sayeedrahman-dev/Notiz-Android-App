package com.sayeed_dev.notiz.model

data class Note(

    val id: Int,
    val title: String,
    val content: String,
    val timestamp: Long,
    val inPinned: Boolean = false,
    val color: Int = 0xFFFFFFFF.toInt()
    
)