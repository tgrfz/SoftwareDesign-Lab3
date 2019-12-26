package com.example.softwaredesign_lab3

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.util.toAndroidPair
import com.example.softwaredesign_lab3.Data.Tag
import kotlinx.android.synthetic.main.fragment_tag.view.*

class MyTagRecyclerViewAdapter(
    private val mValues: MutableList<Tag>,
    private val mListener: TagFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyTagRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val mOnLongClickListener: View.OnLongClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            mListener?.onListFragmentClick(v)
        }
        mOnLongClickListener = View.OnLongClickListener { v ->
            val item = v.tag as Tag
            mListener!!.onListFragmentLongClick(item)
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
        holder.mView.setBackgroundColor(if (item.selected) Color.GRAY else Color.WHITE)
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
