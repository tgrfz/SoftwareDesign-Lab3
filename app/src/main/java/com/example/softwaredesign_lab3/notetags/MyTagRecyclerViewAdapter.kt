package com.example.softwaredesign_lab3.notetags

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.softwaredesign_lab3.R
import com.example.softwaredesign_lab3.model.Tag
import kotlinx.android.synthetic.main.fragment_tag.view.*

class MyTagRecyclerViewAdapter(
    private val mValues: List<Tag>,
    private val mListener: TagFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyTagRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Tag
            item.selected = !item.selected
            notifyDataSetChanged()
            mListener?.onListFragmentClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tag, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mTagView.text = item.name
        holder.mView.setBackgroundColor(if (item.selected) Color.GRAY else android.R.attr.colorBackground)
        holder.mTagView.setTextColor(if (item.selected) Color.WHITE else Color.BLACK)
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTagView: TextView = mView.item_tag
    }
}
