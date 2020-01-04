package com.example.softwaredesign_lab3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.softwaredesign_lab3.App
import com.example.softwaredesign_lab3.model.Note

class TagListViewModel(application: Application) : AndroidViewModel(application) {

    private val storage by lazy { getApplication<App>().storage }
    private val tags: MutableLiveData<MutableList<String>> =
        MutableLiveData(storage.getTags().toMutableList())

    private fun onChange() {
        storage.saveTags(tags.value!!)
        tags.value = tags.value
    }

    fun getTags(): LiveData<MutableList<String>> {
        return tags
    }

    fun addTag(tag: String): Boolean {
        val sameTag = tags.value?.indexOfFirst { it == tag }
        if (sameTag != null && sameTag != -1) {
            return false
        }
        tags.value!!.add(tag)
        onChange()
        return true
    }

    fun deleteTag(tag: String) {
        tags.value!!.removeIf {
            it == tag
        }
        onChange()
    }
}