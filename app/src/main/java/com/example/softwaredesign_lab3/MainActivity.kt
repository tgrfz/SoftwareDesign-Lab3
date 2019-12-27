package com.example.softwaredesign_lab3

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.example.softwaredesign_lab3.alltags.AllTagFragment
import com.example.softwaredesign_lab3.model.Note
import com.example.softwaredesign_lab3.model.Tag
import kotlinx.android.synthetic.main.activity_main.*

private const val NOTE_REQUEST = 42

class MainActivity : AppCompatActivity(), RecordFragment.OnListFragmentInteractionListener,
    AllTagFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var toolbar = findViewById<Toolbar>(R.id.app_bar_main)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = "Notes"

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
    }

    fun onAddClick(view: View) {
        startNoteActivity(this.notes_fragment, NOTE_REQUEST, -1, Note())
    }

    override fun onListFragmentInteraction(position: Int, item: Note?) {
        startNoteActivity(this.notes_fragment, NOTE_REQUEST, position, item)
    }

    override fun onListFragmentClick(item: Tag) {
        (notes_fragment as RecordFragment).setNoteTag(item.name)
        main_drawer.closeDrawer(GravityCompat.START)
    }

}
