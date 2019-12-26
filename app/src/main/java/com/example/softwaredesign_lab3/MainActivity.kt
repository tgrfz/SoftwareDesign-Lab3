package com.example.softwaredesign_lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewParent
import com.example.softwaredesign_lab3.Data.Note
import com.example.softwaredesign_lab3.Data.Tag
import kotlinx.android.synthetic.main.activity_main.*

private const val NOTE_REQUEST = 42

class MainActivity : AppCompatActivity(), RecordFragment.OnListFragmentInteractionListener, TagFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onAddClick(view: View) {
        startNoteActivity(this.notes_fragment, NOTE_REQUEST, -1, Note())
    }

    override fun onListFragmentInteraction(position: Int, item: Note?) {
        startNoteActivity(this.notes_fragment, NOTE_REQUEST, position, item)
    }

    override fun onListFragmentClick(view: View) {
        //TODO
        Log.println(Log.INFO, "Click", "MainActivity")

    }

    override fun onListFragmentLongClick(item: Tag): Boolean {
        //TODO
        return true
    }
}
