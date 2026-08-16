package com.sayeed_dev.notiz.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sayeed_dev.notiz.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao{
    //automatic sob ui update kra
    @Query("SELECT * FROM notes ORDER by isPinned DESC, timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>
    //new note save korar jnno ba update krar jnno ai logic
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    //Note delete krar jnno ai logic
    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): Note?

    //nirdisto kno note search krar jnno
    @Query("SELECT * FROM notes WHERE title LIKE :searchQuery OR content LIKE :searchQuery")
    fun searchNotes(searchQuery: String): Flow<List<Note>>
}