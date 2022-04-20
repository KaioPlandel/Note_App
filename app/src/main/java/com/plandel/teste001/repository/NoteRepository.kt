package com.plandel.teste001.repository

import androidx.lifecycle.LiveData
import com.plandel.teste001.data.NoteDao
import com.plandel.teste001.model.Note

class NoteRepository(private val notesDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insertNote(note: Note){
        notesDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note){
        notesDao.delete(note)
    }

    suspend fun update(note: Note){
        notesDao.update(note)
    }
}