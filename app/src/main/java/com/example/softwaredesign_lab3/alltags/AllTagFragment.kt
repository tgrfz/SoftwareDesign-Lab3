package com.example.softwaredesign_lab3.alltags

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.softwaredesign_lab3.R
import com.example.softwaredesign_lab3.model.Tag
import com.example.softwaredesign_lab3.viewmodel.TagListViewModel

class AllTagFragment : Fragment() {

    private var listAdapter: MyAllTagRecyclerViewAdapter? = null
    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var model: TagListViewModel
    private lateinit var tagList: MutableList<Tag>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model = ViewModelProviders.of(requireActivity())[TagListViewModel::class.java]
        tagList = model.getTags().value!!.map { Tag(it, false) }.toMutableList()
        tagList.add(0, Tag(null, true))
        model.getTags().observe(
            this,
            Observer<MutableList<String>> { tags ->
                tagList = tags.map { Tag(it, false) }.toMutableList()
                tagList.add(0, Tag(null, true))
                listAdapter?.update(tagList)
            })

        val view = inflater.inflate(R.layout.fragment_all_tag_list, container, false)
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                listAdapter =
                    MyAllTagRecyclerViewAdapter(
                        tagList,
                        listener,
                        ::onDelete
                    )
                adapter = listAdapter
            }
        }
        return view
    }

    fun addTag(tag: String) {
        if (tag == "") {
            Toast.makeText(activity, "You can't add an empty tag.", Toast.LENGTH_LONG).show()
        }
        if (!model.addTag(tag)) {
            Toast.makeText(activity, "This tag already exists.", Toast.LENGTH_LONG).show()
        }
    }

    private fun onDelete(tag: String?) {
        if (tag != null) {
            model.deleteTag(tag)
        } else {
            Toast.makeText(activity, "You can't do it.", Toast.LENGTH_LONG).show()
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
