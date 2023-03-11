package com.project.mathg.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.project.mathg.databinding.GridItemsBinding
import com.project.mathg.model.ListItem

class RecyclerViewViewHolder(private var binding: GridItemsBinding) : ViewHolder(binding.root) {
    fun bindTo(listItem: ListItem, itemChecker: RecyclerViewAdapter.ItemChecker) {
        binding.tvUnknown.id = listItem.id
        binding.tvUnknown.isClickable = listItem.isClickable
        binding.tvUnknown.text = listItem.answer
        android.os.Handler().postDelayed({
            listItem.isClickable = true
            listItem.isCardBack = true
            binding.tvUnknown.text = "?"
        }, 5000)
        binding.tvUnknown.setOnClickListener {
            if (!listItem.isMatch) {
                binding.tvUnknown.text = listItem.answer
                itemChecker.clicked(listItem, adapterPosition)
            }
        }
    }
}
