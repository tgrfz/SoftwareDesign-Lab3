package com.example.softwaredesign_lab3.notetags

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.softwaredesign_lab3.NoteActivity
import com.example.softwaredesign_lab3.R
import com.example.softwaredesign_lab3.model.Tag
import com.example.softwaredesign_lab3.viewmodel.TagListViewModel

class TagFragment : Fragment() {

    private var listAdapter: MyTagRecyclerViewAdapter? = null
    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var allTags: List<Tag>
    private lateinit var model: TagListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model = ViewModelProviders.of(requireActivity())[TagListViewModel::class.java]
        allTags = model.getTags().value!!.map { Tag(it, false) }
        model.getTags().observe(
            this,
            Observer<MutableList<String>> { tags ->
                allTags = tags.map { Tag(it, false) }
            })

        val view = inflater.inflate(R.layout.fragment_tag_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                listAdapter =
                    MyTagRecyclerViewAdapter(
                        allTags,
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
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString().plus(" must implement OnListFragmentInteractionListener"))
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentClick(item: Tag)
    }

}
