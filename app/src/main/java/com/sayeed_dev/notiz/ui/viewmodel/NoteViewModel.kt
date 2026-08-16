package com.sayeed_dev.notiz.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.sayeed_dev.notiz.data.NoteDatabase
import com.sayeed_dev.notiz.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application){
    private val noteDao = NoteDatabase.getDatabase(application).noteDao()

    //search query store krar jnno state aita
    val searchQuery = MutableStateFlow(" ")

    //reactive note list
    val allNotes = searchQuery.flatMapLatest { query ->
        if (query.isEmpty()){
            noteDao.getAllNotes()
        }else{
            noteDao.searchNotes("%$query%")
        }
    }

    //search query update krar method
    fun onSearchQueryChanged(query : String){
        searchQuery.value  = query
    }
    fun saveNote(id: Int = 0, title: String, content: String, isPinned: Boolean){
        viewModelScope.launch {
            val note = Note(
                id = id,
                title = title,
                content = content,
                timestamp = System.currentTimeMillis(),
                isPinned = isPinned
            )
            noteDao.insertNote(note)
        }
    }

    // নির্দিষ্ট একটি নোট আইডি দিয়ে খুঁজে বের করা
    suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    //Note delete krar jnno ai logic
    fun deleteNoteById(id: Int){
        viewModelScope.launch {
            noteDao.deleteNoteById(id)
        }
    }

    //Note Status ba pinned korar logic
    fun togglePin(note: Note){
        viewModelScope.launch {
            noteDao.insertNote(note.copy(isPinned = !note.isPinned))
        }
    }
}