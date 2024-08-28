package com.example.noteappwithcompose.data.repository

import com.example.noteappwithcompose.data.db.Note
import com.example.noteappwithcompose.data.db.NoteDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(val noteDao: NoteDao) {

    suspend fun update (note: Note) = noteDao.update(note)
    suspend fun delete(note: Note) = noteDao.deleteNote(note)
    suspend fun insert(note: Note) = noteDao.insert(note)
    suspend fun getAllNotes() = noteDao.getAll()
    suspend fun deleteAllNotes() = noteDao.deleteAll()
    suspend fun getNoteById(id: Int) = noteDao.getById(id)
    suspend fun deleteNoteById(id: Int) = noteDao.deleteById(id)

    suspend fun getFavorites(): List<Note>{
        return noteDao.getFavorites()
    }



}