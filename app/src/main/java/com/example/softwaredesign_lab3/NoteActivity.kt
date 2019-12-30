package com.example.softwaredesign_lab3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.softwaredesign_lab3.model.Note
import com.example.softwaredesign_lab3.model.Tag
import com.example.softwaredesign_lab3.notes.createResultForRecordFragment
import com.example.softwaredesign_lab3.notetags.TagFragment
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
        (note_tags_fragment as TagFragment).update(tags)

        val toolbar = findViewById<Toolbar>(R.id.app_bar_note)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = if (titleEdit.text.equals("")) "Add note" else "Edit note"
        actionBar?.setDisplayHomeAsUpEnabled(true)

//        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
//            this,
//            note_drawer,
//            toolbar,
//            R.string.drawer_open,
//            R.string.drawer_close
//        ) { }
//
//        drawerToggle.isDrawerIndicatorEnabled = true
//        note_drawer.addDrawerListener(drawerToggle)
//        drawerToggle.syncState()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onSaveClick(view: View) {
        setResult(
            RESULT_OK,
            createResultForRecordFragment(
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

    override fun onListFragmentClick(item: Tag) {
        if (item.selected) {
            tags.add(item.name!!)
        } else {
            tags.remove(item.name)
        }
    }
}

fun startNoteActivity(frg: Fragment, code: Int, position: Int, note: Note?) {
    val intent = Intent(frg.context, NoteActivity::class.java)
        .putExtra(NOTE_KEY, note).putExtra(POSITION_KEY, position)

    frg.startActivityForResult(intent, code)
}