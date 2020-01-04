package com.example.softwaredesign_lab3.notes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.softwaredesign_lab3.R
import com.example.softwaredesign_lab3.model.Note
import com.example.softwaredesign_lab3.viewmodel.NoteListViewModel
import java.time.format.DateTimeFormatter

private const val NOTE_KEY = "note"
private const val NOTE_REQUEST = 42

class NoteFragment : Fragment() {

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    private var listAdapter: MyNoteRecyclerViewAdapter? = null
    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var allNotes: MutableList<Note>
    private lateinit var model: NoteListViewModel
    private var curTag: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model = ViewModelProviders.of(requireActivity())[NoteListViewModel::class.java]
        allNotes = model.getNotes().value!!
        model.getNotes().observe(
            this,
            Observer<MutableList<Note>> { notes ->
                allNotes = notes
                setNoteTag(curTag)
            })

        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                listAdapter =
                    MyNoteRecyclerViewAdapter(
                        allNotes.toMutableList(), listener, ::onDelete
                    )
                adapter = listAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString().plus(" must implement OnListFragmentInteractionListener"))
        }

    }

    fun setNoteTag(tag: String?) {
        curTag = tag
        tag?.let {
            listAdapter?.setTag(allNotes.filter { tag in it.tags })
        } ?: listAdapter?.setTag(allNotes)
    }

    fun onSearch(str: String) {
        curTag?.let { curTag ->
            listAdapter?.setTag(allNotes.filter { curTag in it.tags && it.title.contains(str) })
        } ?: listAdapter?.setTag(allNotes.filter { it.title.contains(str) })
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun onDelete(note: Note) {
        model.deleteNote(note)
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(position: Int, item: Note?)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                NOTE_REQUEST -> {
                    val note = data.getParcelableExtra<Note>(NOTE_KEY)!!
                    if (note.title == "") {
                        note.title = note.date.format(formatter)
                    }
                    model.updateNote(note)
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}

fun createResultForRecordFragment(note: Note): Intent {
    return Intent().putExtra(NOTE_KEY, note)
}