package com.example.softwaredesign_lab3

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.softwaredesign_lab3.model.Note

private const val NOTE_KEY = "note"
private const val POSITION_KEY = "position"
private const val NOTE_REQUEST = 42

class RecordFragment : Fragment() {

    private var listAdapter: MyRecordRecyclerViewAdapter? = null

    private var listener: OnListFragmentInteractionListener?= null

    private val allNotes = listOf(
        Note("qew", "qwer", mutableListOf("tag1")),
        Note("qehgvhjw", "qwehgyjr", mutableListOf("tag1", "tag2"))
    )
    private var curTag: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//      TODO  context.getSharedPreferences()
        val view = inflater.inflate(R.layout.fragment_record_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                listAdapter = MyRecordRecyclerViewAdapter(
                    allNotes.toMutableList(), listener
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
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }

    }

    fun setNoteTag(tag: String?) {
        curTag = tag
        tag?.let {
            listAdapter?.setTag(allNotes.filter { tag in it.tags })
        } ?: listAdapter?.setTag(allNotes)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(position: Int, item: Note?)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                NOTE_REQUEST -> {
                    val position = data.getIntExtra(POSITION_KEY, -1)
                    val note = data.getParcelableExtra<Note>(NOTE_KEY)!!
                    listAdapter?.setNote(position, note)
                    setNoteTag(curTag)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}

fun createResultForRecordFragment(position: Int, note: Note): Intent {
    return Intent().putExtra(NOTE_KEY, note).putExtra(POSITION_KEY, position)
}