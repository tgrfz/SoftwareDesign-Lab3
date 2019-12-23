package com.example.softwaredesign_lab3.Data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    var title: String,
    var content: String,
    var tags: List<String>,
    val date: Date = Date()
) : Parcelable {

    constructor() : this("", "", emptyList())
}