package com.project.mathg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.mathg.databinding.GridItemsBinding
import com.project.mathg.model.ListItem

class RecyclerViewAdapter(
    private var list: ArrayList<ListItem>, private val itemChecker: ItemChecker
) : RecyclerView.Adapter<RecyclerViewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val binding = GridItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bindTo(list[position], itemChecker)
    }

    override fun getItemCount(): Int {
        return 16
    }

    interface ItemChecker {
        fun clicked(listItem: ListItem, position: Int)
    }
}