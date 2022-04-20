package com.plandel.teste001.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.plandel.teste001.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notesTable")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)
}