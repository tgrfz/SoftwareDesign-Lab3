package com.example.softwaredesign_lab3.notetags

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.softwaredesign_lab3.NoteActivity
import com.example.softwaredesign_lab3.R
import com.example.softwaredesign_lab3.model.Tag

class TagFragment : Fragment() {

    private var listAdapter: MyTagRecyclerViewAdapter? = null
    private lateinit var noteActivity: NoteActivity

    private var listener: OnListFragmentInteractionListener? = null
    //if (this@TagFragment.context?.resources?.getResourceEntryName((parentView as View).id) == "main_drawer") {

private val allTags = listOf("tag1", "tag2").map { Tag(it, false) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tag_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                listAdapter =
                    MyTagRecyclerViewAdapter(
                        allTags.toMutableList(),
                        listener
                    )
                adapter = listAdapter
            }
        }
        return view
    }

    fun update(tags: List<String>) {
        allTags.forEach {
            it.selected = it.name in tags
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        noteActivity = context as NoteActivity
        listener = context
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentClick(item: Tag)
    }

}
