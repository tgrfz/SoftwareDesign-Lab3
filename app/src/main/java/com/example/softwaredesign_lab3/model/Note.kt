package com.example.softwaredesign_lab3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Note(
    var title: String,
    var content: String,
    var tags: MutableList<String>,
    val date: LocalDateTime = LocalDateTime.now()
) : Parcelable {

    constructor() : this("", "", mutableListOf())

    fun update(note: Note) {
        title = note.title
        content = note.content
        tags = note.tags
    }
}