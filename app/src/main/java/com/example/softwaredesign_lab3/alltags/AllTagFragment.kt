package com.example.softwaredesign_lab3.alltags

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.softwaredesign_lab3.R
import com.example.softwaredesign_lab3.model.Tag

class AllTagFragment : Fragment() {

    private var listAdapter: MyAllTagRecyclerViewAdapter? = null

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_tag_list, container, false)
        val tagList = listOf("tag1", "tag2").map { Tag(it, false) }.toMutableList()
        tagList.add(0, Tag(null, true))
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                listAdapter =
                    MyAllTagRecyclerViewAdapter(
                        tagList,
                        listener
                    )
                adapter = listAdapter
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

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentClick(item: Tag)
    }

}
