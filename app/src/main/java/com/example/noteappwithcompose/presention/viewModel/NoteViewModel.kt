package com.example.noteappwithcompose.presention.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappwithcompose.data.db.Note
import com.example.noteappwithcompose.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(val noteRepository: NoteRepository): ViewModel(){


    private val _allNotes = MutableStateFlow<List<Note>?>(null)
    val allNotes: StateFlow<List<Note>?> get() = _allNotes

    init {
        getAllNotes()
    }

    // Function to filter notes by search query
    fun searchNotes(query: String): StateFlow<List<Note>?> {
        return allNotes.map { notes ->
            notes?.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun getAllNotes() {
        viewModelScope.launch {
            _allNotes.value = noteRepository.getAllNotes()
            println("All notes: ${_allNotes.value}")
        }
    }
    fun insertNote(note: Note){
        viewModelScope.launch {
            noteRepository.insert(note)
            getAllNotes()
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch {
            noteRepository.update(note)
            getAllNotes()
        }
    }

    fun getNoteById(id:Int){
        viewModelScope.launch {
            noteRepository.getNoteById(id)
            getAllNotes()
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteRepository.delete(note)
            getAllNotes()
        }
    }
}