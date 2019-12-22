package com.example.softwaredesign_lab3.Data

import java.util.*

data class Note(var title: String, var content: String, var tags: List<String>, val date: Date = Date()) {

}