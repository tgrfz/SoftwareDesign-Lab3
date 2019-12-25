package com.example.softwaredesign_lab3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.softwaredesign_lab3.Data.Note

private const val NOTE_KEY = "note"
private const val POSITION_KEY = "position"
private const val NOTE_REQUEST = 42

class RecordFragment : Fragment() {

    private var listAdapter: MyRecordRecyclerViewAdapter? = null

    private var listener = object : OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(position: Int, item: Note?) {
            startNoteActivity(this@RecordFragment, NOTE_REQUEST, position, item)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_record_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                listAdapter = MyRecordRecyclerViewAdapter(
                    mutableListOf(
                        Note("qew", "qwer", emptyList()),
                        Note("qehgvhjw", "qwehgyjr", listOf("1", "@"))
                    ), listener
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
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}

fun createResultForRecordFragment(position: Int, note: Note): Intent {
    return Intent().putExtra(NOTE_KEY, note).putExtra(POSITION_KEY, position)
}