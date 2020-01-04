package com.example.softwaredesign_lab3.alltags

import android.graphics.Color
import android.view.ContextMenu
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.softwaredesign_lab3.R
import com.example.softwaredesign_lab3.model.Tag
import kotlinx.android.synthetic.main.fragment_tag.view.*

class MyAllTagRecyclerViewAdapter(
    private val mValues: MutableList<Tag>,
    private val mListener: AllTagFragment.OnListFragmentInteractionListener?,
    private val mDeleteListener: (String?) -> Unit
) : RecyclerView.Adapter<MyAllTagRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Tag
            mValues.forEach { it.selected = false }
            item.selected = true
            notifyDataSetChanged()
            mListener?.onListFragmentClick(item)
        }
    }

    fun update(newTags: List<Tag>) {
        mValues.clear()
        mValues.addAll(newTags)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_all_tag, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mTagView.text = item.name ?: "All tags"
        holder.mView.setBackgroundColor(if (item.selected) Color.GRAY else android.R.attr.colorBackground)
        holder.mTagView.setTextColor(if (item.selected) Color.WHITE else Color.BLACK)
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView), View.OnCreateContextMenuListener {
        val mTagView: TextView = mView.item_tag

        init {
            mView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu!!.add("Delete").setOnMenuItemClickListener {
                mDeleteListener(mValues[adapterPosition].name)
                true
            }
        }
    }
}
