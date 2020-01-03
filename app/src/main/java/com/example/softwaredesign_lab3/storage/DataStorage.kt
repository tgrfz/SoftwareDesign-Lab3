package com.example.softwaredesign_lab3.storage

import android.content.Context
import androidx.core.content.edit
import com.example.softwaredesign_lab3.model.Note
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime

private const val PREF_NAME = "tiger"
private const val NOTES_KEY = "notes"
private const val TAGS_KEY = "tags"

class DataStorage(context: Context) {

    private val manager by lazy {
        context.getSharedPreferences(context.packageName + PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getNotes(): List<Note> {
        val notesJson = JSONArray(getString(NOTES_KEY) ?: "[]")
        val notes = mutableListOf<Note>()
        repeat(notesJson.length()) { i ->
            val noteJson = notesJson.getJSONObject(i)
            notes.add(Note(
                noteJson.getString("title"),
                noteJson.getString("content"),
                noteJson.getJSONArray("tags").let { tags ->
                    mutableListOf<String>().apply {
                        repeat(tags.length()) { i ->
                            add(tags.getString(i))
                        }
                    }
                },
                LocalDateTime.parse(noteJson.getString("date"))
            ))
        }
        return notes
    }

    fun saveNotes(notes: List<Note>) {
        saveString(NOTES_KEY, JSONArray().apply {
            notes.forEach {
                put(JSONObject().apply {
                    put("title", it.title)
                    put("content", it.content)
                    put("tags", JSONArray().apply {
                        it.tags.forEach { put(it) }
                    })
                    put("date", it.date.toString())
                })
            }
        }.toString())
    }

    fun getTags(): List<String> {
        val tagsJson = JSONArray(getString(TAGS_KEY) ?: "[]")
        val tags = mutableListOf<String>()
        repeat(tagsJson.length()) { i ->
            tags.add(tagsJson.getString(i))
        }
        return tags
    }

    fun saveTags(tags: List<String>) {
        saveString(TAGS_KEY, JSONArray().apply {
            tags.forEach { put(it) }
        }.toString())
    }

    private fun getString(key: String, defValue: String? = null) = manager.getString(key, defValue)

    private fun saveString(key: String, value: String?) {
        manager.edit { putString(key, value) }
    }
}