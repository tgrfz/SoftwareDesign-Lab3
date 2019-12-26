package com.example.softwaredesign_lab3

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.softwaredesign_lab3.Data.Note
import com.example.softwaredesign_lab3.RecordFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_record.view.*
import java.time.format.DateTimeFormatter

class MyRecordRecyclerViewAdapter(
    private val mValues: MutableList<Note>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyRecordRecyclerViewAdapter.ViewHolder>() {

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val (item, position) = v.tag as Pair<Note, Int>
            mListener?.onListFragmentInteraction(position, item)
        }
    }

    fun setNote(position: Int, note: Note) {
        if (note.title == "") {
            note.title = note.date.format(formatter)
        }
        if (position == -1) {
            mValues.add(note)
            notifyDataSetChanged()
        } else {
            mValues[position] = note
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_record, parent, false)
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
