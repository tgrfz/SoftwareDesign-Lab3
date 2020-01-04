package com.example.softwaredesign_lab3

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.widget.doOnTextChanged
import com.example.softwaredesign_lab3.alltags.AllTagFragment
import com.example.softwaredesign_lab3.model.Note
import com.example.softwaredesign_lab3.model.Tag
import com.example.softwaredesign_lab3.notes.NoteFragment
import kotlinx.android.synthetic.main.activity_main.*


private const val NOTE_REQUEST = 42

class MainActivity : AppCompatActivity(), NoteFragment.OnListFragmentInteractionListener,
    AllTagFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.app_bar_main)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = "All notes"

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            main_drawer,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {}

        drawerToggle.isDrawerIndicatorEnabled = true
        main_drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        search_input.doOnTextChanged { text, _, _, _ ->
            (this.notes_fragment as NoteFragment).onSearch(
                text.toString()
            )
        }
    }

    fun onAddClick(view: View) {
        startNoteActivity(this.notes_fragment, NOTE_REQUEST, -1, Note())
    }

    fun onAddTagClick(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("New tag:")
        val input = EditText(this)
        input.isFocusableInTouchMode = true
        input.requestFocus()
        builder.setView(input)
        builder.setPositiveButton("Add") { _, _ ->
            val tag = input.text.toString()
            (all_tags_fragment as AllTagFragment).addTag(tag)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()

        (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            0
        )
    }

    override fun onListFragmentInteraction(position: Int, item: Note?) {
        startNoteActivity(this.notes_fragment, NOTE_REQUEST, position, item)
    }

    override fun onListFragmentClick(item: Tag) {
        (notes_fragment as NoteFragment).setNoteTag(item.name)
        supportActionBar?.title = item.name ?: "All notes"
        this.search_input.setText("")
        this.search_input.clearFocus()
        main_drawer.closeDrawer(GravityCompat.START)
    }

}
