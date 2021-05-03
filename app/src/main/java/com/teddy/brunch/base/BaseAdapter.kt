package com.teddy.brunch.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>() : RecyclerView.Adapter<VH>() {

    var onClickListener: ((T) -> Unit)? = null

    private val _list = mutableListOf<T>()
    val list get() = _list as List<T>

    fun addAll(items: Collection<T>, notify: Boolean = true) {
        _list.addAll(items)
        if (notify)
            notifyDataSetChanged()
    }

    fun addAll(items: Array<T>, notify: Boolean = true) {
        _list.addAll(items)
        if (notify)
            notifyDataSetChanged()
    }

    fun add(item: T, notify: Boolean = true) {
        _list.add(item)
        if (notify) {
            val position = _list.indexOf(item)
            notifyItemInserted(position)
            notifyDataSetChanged()
        }
    }

    fun update(items: Collection<T>, notify: Boolean = true) {
        _list.clear()
        _list.addAll(items)
        if (notify) notifyDataSetChanged()
    }

    fun remove(position: Int) {
        _list.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun clear(notify: Boolean = true) {
        _list.clear()
        if (notify) notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    abstract override fun onBindViewHolder(holder: VH, position: Int)
}