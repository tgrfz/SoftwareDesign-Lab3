package com.example.softwaredesign_lab3

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.widget.ButtonBarLayout
import androidx.fragment.app.Fragment
import com.example.softwaredesign_lab3.Data.Note
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*

private const val NOTE_KEY = "note"
private const val POSITION_KEY = "position"

class NoteActivity : AppCompatActivity() {

    private var date: Date? = null
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        position = intent.getIntExtra(POSITION_KEY, -1)
        intent.getParcelableExtra<Note>(NOTE_KEY)?.run {
            titleEdit.setText(title)
            contentEdit.setText(content)
            this@NoteActivity.date = date
        }
    }

    fun onSaveClick(view: View) {
        setResult(
            RESULT_OK, createResultForRecordFragment(
                position,
                Note(
                    titleEdit.text.toString(),
                    contentEdit.text.toString(),
                    emptyList(),
                    date ?: Date()
                )
            )
        )
        finish()
    }

    fun onCancelClick(view: View) {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}

fun startNoteActivity(frg: Fragment, code: Int, position: Int, note: Note?) {
    val intent = Intent(frg.context, NoteActivity::class.java)
        .putExtra(NOTE_KEY, note).putExtra(POSITION_KEY, position)

    frg.startActivityForResult(intent, code)

}