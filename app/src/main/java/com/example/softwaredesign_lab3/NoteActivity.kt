package com.example.softwaredesign_lab3

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.softwaredesign_lab3.Data.Note
import com.example.softwaredesign_lab3.Data.Tag
import kotlinx.android.synthetic.main.activity_note.*
import java.time.LocalDateTime

private const val NOTE_KEY = "note"
private const val POSITION_KEY = "position"

class NoteActivity : AppCompatActivity(), TagFragment.OnListFragmentInteractionListener {

    private var date: LocalDateTime? = null
    private var position: Int = -1
    private var tags: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        position = intent.getIntExtra(POSITION_KEY, -1)
        intent.getParcelableExtra<Note>(NOTE_KEY)?.run {
            titleEdit.setText(title)
            contentEdit.setText(content)
            this@NoteActivity.date = date
            this@NoteActivity.tags = tags
        }
    }

    fun onSaveClick(view: View) {
        setResult(
            RESULT_OK, createResultForRecordFragment(
                position,
                Note(
                    titleEdit.text.toString(),
                    contentEdit.text.toString(),
                    tags,
                    date ?: LocalDateTime.now()
                )
            )
        )
        finish()
    }

    fun onCancelClick(view: View) {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun onListFragmentClick(view: View) {
        val item = view.tag as Tag
        item.selected = !item.selected
        view.setBackgroundColor(if (item.selected) Color.GRAY else Color.WHITE)
        if (item.selected) {
            tags.add(item.name)
        } else {
            tags.remove(item.name)
        }
    }

    override fun onListFragmentLongClick(item: Tag): Boolean {
        //TODO
        return true
    }
}

fun startNoteActivity(frg: Fragment, code: Int, position: Int, note: Note?) {
    val intent = Intent(frg.context, NoteActivity::class.java)
        .putExtra(NOTE_KEY, note).putExtra(POSITION_KEY, position)

    frg.startActivityForResult(intent, code)
}