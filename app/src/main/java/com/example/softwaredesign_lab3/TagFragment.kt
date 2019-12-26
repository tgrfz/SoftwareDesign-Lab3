package com.example.softwaredesign_lab3

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import com.example.softwaredesign_lab3.Data.Tag
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_note.*

class TagFragment : Fragment() {

    private var listAdapter: MyTagRecyclerViewAdapter? = null

    private var listener: OnListFragmentInteractionListener? = null
    //if (this@TagFragment.context?.resources?.getResourceEntryName((parentView as View).id) == "main_drawer") {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tag_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                listAdapter = MyTagRecyclerViewAdapter(listOf("tag1", "tag2").map { Tag(it, false) }.toMutableList(), listener)
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
        fun onListFragmentClick(view: View)
        fun onListFragmentLongClick(item: Tag): Boolean
    }

}
