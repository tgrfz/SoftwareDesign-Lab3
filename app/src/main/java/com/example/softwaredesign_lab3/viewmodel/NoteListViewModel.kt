package com.example.softwaredesign_lab3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.softwaredesign_lab3.model.Note
import java.time.LocalDateTime

class NoteListViewModel : ViewModel() {
    private val notes: MutableLiveData<MutableList<Note>> by lazy {
        MutableLiveData<MutableList<Note>>(
            mutableListOf(
                Note("qew", "qwer", mutableListOf("tag1"), LocalDateTime.of(2020, 1, 1, 12, 12)),
                Note("qehgvhjw", "qwehgyjr", mutableListOf("tag1", "tag2"))
            )
        )
    }

    fun notifyObserver() {
        notes.value = notes.value
    }

    fun getNotes(): LiveData<MutableList<Note>> {
        return notes
    }

    fun updateNote(note: Note) {
        val oldNote = notes.value?.find { n -> n.date == note.date }
        if (oldNote != null) {
            notes.value!![notes.value!!.indexOf(oldNote)] = note
        } else {
            notes.value!!.add(note)
        }
        notifyObserver()
    }
}