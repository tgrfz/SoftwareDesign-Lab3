package com.example.softwaredesign_lab3

import android.content.Context
import java.io.File

abstract class DataStorage(context: Context, filename: String) {

    private val file = File(context.filesDir, filename)
    private var text: String? = null

    protected fun read(): String {
        if (text == null) {
            text = file.readText()
        }
        return text!!
    }

    protected fun write(str: String) {
        text = str
        file.writeText(str)
    }
}