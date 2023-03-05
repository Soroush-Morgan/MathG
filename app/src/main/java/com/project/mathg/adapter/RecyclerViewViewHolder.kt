package com.project.mathg.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.project.mathg.databinding.GridItemsBinding
import com.project.mathg.model.ListItem

class RecyclerViewViewHolder(private var binding: GridItemsBinding) : ViewHolder(binding.root) {
    fun bindTo(listItem: ListItem, itemChecker: RecyclerViewAdapter.ItemChecker) {
        var count = 0
        binding.tvUnknown.text = listItem.answer
        binding.tvUnknown.isClickable = listItem.isClickable
        binding.tvUnknown.id = listItem.id
        //binding.tvUnknown.text = "?"
        binding.tvUnknown.setOnClickListener {
            binding.tvUnknown.text = listItem.answer
            itemChecker.firstClicked(listItem)
        }
    }
}
