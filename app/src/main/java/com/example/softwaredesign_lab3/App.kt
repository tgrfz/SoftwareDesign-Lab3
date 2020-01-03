package com.example.softwaredesign_lab3

import android.app.Application
import com.example.softwaredesign_lab3.storage.DataStorage

class App : Application() {

    val storage by lazy { DataStorage(applicationContext) }
}