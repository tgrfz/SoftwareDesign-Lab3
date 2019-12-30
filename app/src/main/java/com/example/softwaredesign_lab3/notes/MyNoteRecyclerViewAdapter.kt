package com.example.softwaredesign_lab3.notes

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.softwaredesign_lab3.R
import com.example.softwaredesign_lab3.model.Note
import com.example.softwaredesign_lab3.notes.RecordFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_note.view.*
import java.time.format.DateTimeFormatter

class MyNoteRecyclerViewAdapter(
    private val mValues: MutableList<Note>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder>() {

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    private val mOnClickListener: View.OnClickListener

    private var curTag: String? = null

    init {
        mOnClickListener = View.OnClickListener { v ->
            val (item, position) = v.tag as Pair<Note, Int>
            mListener?.onListFragmentInteraction(position, item)
        }
    }

    fun setTag(notes: List<Note>) {
        mValues.clear()
        mValues.addAll(notes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mTitleView.text = item.title
        holder.mDateView.text = item.date.format(formatter)
        holder.mTagsView.text = item.tags.joinToString(", #")
        if (holder.mTagsView.text != "")
            holder.mTagsView.text = "#".plus(holder.mTagsView.text)

        with(holder.mView) {
            tag = item to position
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTitleView: TextView = mView.title
        val mDateView: TextView = mView.date
        val mTagsView: TextView = mView.tags
    }
}
