package com.example.softwaredesign_lab3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.softwaredesign_lab3.App
import com.example.softwaredesign_lab3.model.Note

class NoteListViewModel(application: Application) : AndroidViewModel(application) {

    private val storage by lazy { getApplication<App>().storage }
    private val notes: MutableLiveData<MutableList<Note>> =
        MutableLiveData(storage.getNotes().toMutableList())

    private fun onChange() {
        storage.saveNotes(notes.value!!)
        notes.value = notes.value
    }

    fun getNotes(): LiveData<MutableList<Note>> {
        return notes
    }

    fun updateNote(note: Note) {
        val oldNoteId = notes.value?.indexOfFirst { it.date == note.date }
        if (oldNoteId != null && oldNoteId != -1) {
            notes.value!![oldNoteId] = note
        } else {
            notes.value!!.add(note)
        }
        onChange()
    }

    fun deleteNote(note: Note) {
        notes.value!!.removeIf {
            it.date == note.date
        }
        onChange()
    }
}